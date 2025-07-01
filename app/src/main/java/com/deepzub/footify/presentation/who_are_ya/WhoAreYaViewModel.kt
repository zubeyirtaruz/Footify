package com.deepzub.footify.presentation.who_are_ya

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository
import com.deepzub.footify.domain.use_case.get_player.GetFootballersUseCase
import com.deepzub.footify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FootballerViewModel @Inject constructor(
    private val getFootballersUseCase: GetFootballersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FootballerState())
    val state: StateFlow<FootballerState> = _state

    fun loadFootballers(league: Int, season: Int) {
        getFootballersUseCase(league, season).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FootballerState(footballers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FootballerState(error = result.message ?: "Error")
                }
                is Resource.Loading -> {
                    _state.value = FootballerState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
