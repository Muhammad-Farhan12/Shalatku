package com.example.shalatku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalatKuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SalatKuUiState())
    val uiState: StateFlow<SalatKuUiState> = _uiState.asStateFlow()

    // Variabel penghitung panggilan API (untuk simulasi HW13)
    private var apiCallCount = 0

    // Satu-satunya fungsi publik (CP3)
    fun onEvent(event: SalatKuEvent) {
        when (event) {
            is SalatKuEvent.PrayerToggled -> togglePrayer(event.name)
            SalatKuEvent.PrayersCleared -> clearPrayers()
            is SalatKuEvent.DaySelected -> selectDay(event.index)
            SalatKuEvent.TasbihIncremented -> incrementTasbih()
            SalatKuEvent.TasbihReset -> resetTasbih()
            is SalatKuEvent.RoundSizeSelected -> setRoundSize(event.size)
            SalatKuEvent.PrayerTimesRequested -> loadPrayerTimes()
        }
    }

    private fun togglePrayer(name: String) {
        _uiState.update { state ->
            val done = if (name in state.donePrayers) {
                state.donePrayers - name
            } else {
                state.donePrayers + name
            }
            state.copy(donePrayers = done)
        }
    }

    private fun clearPrayers() {
        _uiState.update { it.copy(donePrayers = emptyList()) }
    }

    private fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDay = index) }
    }

    private fun incrementTasbih() {
        _uiState.update { state ->
            val next = state.tasbihCount + 1
            if (next == state.roundSize) {
                state.copy(tasbihCount = 0, completedRounds = state.completedRounds + 1)
            } else {
                state.copy(tasbihCount = next)
            }
        }
    }

    private fun resetTasbih() {
        _uiState.update { it.copy(tasbihCount = 0, completedRounds = 0) }
    }

    private fun setRoundSize(size: Int) {
        _uiState.update { it.copy(roundSize = size, tasbihCount = 0) }
    }

    // HW13: Logika loading, error, dan success
    private fun loadPrayerTimes() {
        if (_uiState.value.prayerTimes is PrayerTimesUiState.Loading) return // Cegah klik ganda

        _uiState.update { it.copy(prayerTimes = PrayerTimesUiState.Loading) }

        viewModelScope.launch {
            delay(1500)
            apiCallCount++

            if (apiCallCount % 2 == 0) {
                // Panggilan genap disimulasikan gagal
                _uiState.update {
                    it.copy(prayerTimes = PrayerTimesUiState.Error("Gagal mengambil data. Coba lagi."))
                }
            } else {
                // Panggilan ganjil sukses
                _uiState.update {
                    it.copy(prayerTimes = PrayerTimesUiState.Success(samplePrayers))
                }
            }
        }
    }
}