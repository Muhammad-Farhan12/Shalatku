package com.example.shalatku

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrayerTimesSection(
    state: PrayerTimesUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is PrayerTimesUiState.Loading -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
                Text("Memuat jadwal shalat...")
            }
            is PrayerTimesUiState.Error -> {
                Text(state.message, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
                Button(onClick = onRefresh) { Text("Coba Lagi (Retry)") }
            }
            is PrayerTimesUiState.Success -> {
                state.times.forEach { prayer ->
                    PrayerCard(prayer) // Menggunakan PrayerCard yang sudah ada
                }
                Spacer(Modifier.height(16.dp))
                Button(onClick = onRefresh) { Text("Refresh Jadwal") }
            }
        }
    }
}