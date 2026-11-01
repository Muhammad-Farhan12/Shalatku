package com.example.shalatku

data class SalatKuUiState(
    val prayers: List<Prayer> = samplePrayers,
    val donePrayers: List<String> = emptyList(),
    val selectedDay: Int = 0,
    val tasbihCount: Int = 0,
    val completedRounds: Int = 0,
    val roundSize: Int = 33,
    // HW13: State default adalah Success memuat samplePrayers
    val prayerTimes: PrayerTimesUiState = PrayerTimesUiState.Success(samplePrayers)
) {
    // CP2: Computed property agar kita tidak menyimpan dua data yang berulang
    val doneCount: Int
        get() = donePrayers.size
}