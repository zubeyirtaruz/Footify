package com.deepzub.footify.presentation.career_path_challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.use_case.get_footballers_for_career_path.GetFootballersForCareerPath
import com.deepzub.footify.domain.use_case.get_player_season_stats.GetPlayerSeasonStats
import com.deepzub.footify.domain.use_case.get_teams_players.GetTeamsPlayers
import com.deepzub.footify.presentation.career_path_challenge.model.ClubEntry
import com.deepzub.footify.presentation.career_path_challenge.model.Footballer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.deepzub.footify.util.Constants
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CareerPathViewModel @Inject constructor(
    private val getFootballers: GetFootballersForCareerPath,
    private val getTeams: GetTeamsPlayers,
    private val getPlayerStats: GetPlayerSeasonStats
) : ViewModel() {

    private val _state = MutableStateFlow(CareerPathState())
    val state: StateFlow<CareerPathState> = _state

    init {
        loadData()
    }

    fun onEvent(event: CareerPathEvent) {
        when (event) {
            is CareerPathEvent.MakeGuess -> {
                val current = _state.value
                val footballer = current.footballer ?: return
                val correct = event.guess.equals(footballer.name, ignoreCase = true)

                val newRevealedCount = when {
                    correct -> footballer.careerPath.size
                    current.revealedCount < footballer.careerPath.size -> current.revealedCount + 1
                    else -> current.revealedCount
                }

                _state.update {
                    it.copy(
                        isCorrect = correct,
                        currentGuess = current.currentGuess + 1,
                        revealedCount = newRevealedCount,
                        isGameOver = correct || newRevealedCount >= footballer.careerPath.size
                    )
                }
            }

            is CareerPathEvent.ToggleHelp -> {
                _state.update { it.copy(showHelp = event.show) }
            }

            is CareerPathEvent.ResetGame -> {
                pickRandomPlayer()
            }
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        // 5 lig futbolcuları paralel Flow
        val leagues = listOf(
            Constants.PREMIER_LEAGUE_ID,
            Constants.BUNDESLIGA_ID,
            Constants.LA_LIGA_ID,
            Constants.LIGUE_1_ID,
            Constants.SERIE_A_ID
        )

        val footballerFlows = leagues.map { league ->
            getFootballers(league, Constants.SEASON_ID)
        }

        // Combine tüm futbolcu Flow’ları
        merge(*footballerFlows.toTypedArray())
            .onEach { res ->
                when (res) {
                    is Resource.Error   -> _state.update { it.copy(error = res.message, isLoading = false) }
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val merged = (_state.value.footballers + res.data.orEmpty())
                            .distinctBy { it.id }
                        _state.update { it.copy(footballers = merged) }
                    }
                }
            }
            .launchIn(viewModelScope)

        /* Flow’lardan ilk veriler gelir gelmez rastgele oyuncu seç */
        viewModelScope.launch {
            state.collect { state ->
                if (state.footballers.isNotEmpty() ) {
                    _state.update { it.copy(isLoading = false) }
                    pickRandomPlayer()
                    cancel()
                }
            }
        }

    }

    private fun pickRandomPlayer() {
        val random = _state.value.footballers.randomOrNull() ?: return
        _state.update { it.copy(player = random) }

        println("New Footballer: ${random.name}")
        println("New Footballer ID: ${random.id}")

        viewModelScope.launch {
            getTeams(random.id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val careerTeams = result.data.orEmpty()

                        val filtered = careerTeams
                            .filterNot {
                                it.name.contains(random.nationality, ignoreCase = true)
                            }
                            .filter { it.seasons.isNotEmpty() }

                        val sorted = filtered.sortedBy { it.seasons.minOrNull() ?: Int.MAX_VALUE }

                        val clubs = mutableListOf<ClubEntry>()

                        sorted.forEach { team ->
                            val yearsRange = when {
                                team.seasons.size == 1 -> team.seasons.first().toString()
                                else -> "${team.seasons.minOrNull()}–${team.seasons.maxOrNull()}"
                            }

                            // Oyuncunun bu takımdaki tüm sezon istatistiklerini al
                            val statsResult = getPlayerStats(team.id, random.name)
                                .first { it is Resource.Success || it is Resource.Error }

                            statsResult.data?.forEach {
                                println("Season: ${it.season}, Apps: ${it.appearances}, Goals: ${it.goals}")
                            }

                            val totalApps = statsResult.data?.sumOf { it.appearances } ?: 0
                            val totalGoals = statsResult.data?.sumOf { it.goals } ?: 0

                            clubs.add(
                                ClubEntry(
                                    years = yearsRange,
                                    team = team.name,
                                    apps = totalApps.toString(),
                                    goals = "($totalGoals)"
                                )
                            )
                        }

                        val player = Footballer(random.name, clubs)

                        _state.update {
                            it.copy(
                                footballer = player,
                                maxGuesses = clubs.size,
                                isLoading = false,
                                currentGuess = 1,
                                isCorrect = false,
                                isGameOver = false,
                                revealedCount = 0
                            )
                        }

                        println("Clubs:")
                        clubs.forEach { println(it) }
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message) }
                        println("Error fetching teams: ${result.message}")
                    }
                }
            }
        }
    }
}