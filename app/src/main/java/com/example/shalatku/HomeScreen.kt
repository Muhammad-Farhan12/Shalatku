package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrayerCard(prayer: Prayer, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(prayer.name, style = MaterialTheme.typography.titleMedium)
            Text(prayer.time, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun HomeScreen(
    prayers: List<Prayer>,
    selectedDay: Int,
    onSelectDay: (Int) -> Unit,
    prayerTimes: PrayerTimesUiState,
    onRefreshPrayerTimes: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Shalatku", style = MaterialTheme.typography.headlineMedium)
        Text("Today's prayer times", style = MaterialTheme.typography.bodyMedium)

        PrayerTimesSection(
            state = prayerTimes,
            onRefresh = onRefreshPrayerTimes
        )
    }
}
