package com.deepzub.footify.presentation.who_are_ya

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.repository.FootballerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WhoAreYaViewModel @Inject constructor(
    private val repository: FootballerRepository
) : ViewModel() {

    private val _footballer = MutableStateFlow<Footballer?>(null)
    val footballer: StateFlow<Footballer?> = _footballer

    fun loadFootballer(playerId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getFootballer(playerId)
                _footballer.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
