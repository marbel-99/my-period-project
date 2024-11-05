package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MonthCarousel(
    selectedMonth: Int,
    selectedYear: Int,
    onMonthSelected: (Int, Int) -> Unit,
    onDaySelected: (LocalDate) -> Unit
) {
    val months = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )
    var currentMonth by remember { mutableStateOf(selectedMonth) }
    var currentYear by remember { mutableStateOf(selectedYear) }
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                if (currentMonth == 1) {
                    currentMonth = 12
                    currentYear -= 1
                } else {
                    currentMonth -= 1
                }
                onMonthSelected(currentMonth, currentYear)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous",
                    tint = Color(0xFF65558F)
                )
            }

            Text(
                text = "${months[currentMonth - 1]} $currentYear",
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color(0xFFAE6BA4)
                )
            )

            IconButton(onClick = {
                if (currentMonth == 12) {
                    currentMonth = 1
                    currentYear += 1
                } else {
                    currentMonth += 1
                }
                onMonthSelected(currentMonth, currentYear)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = Color(0xFF65558F)
                )
            }
        }
        CalendarView(
            currentMonth = currentMonth,
            currentYear = currentYear,
            selectedDay = selectedDay,
            onDaySelected = { date ->
                selectedDay = date
                onDaySelected(date)
            }
        )
    }
}

@Composable
fun CalendarView(
    currentMonth: Int,
    currentYear: Int,
    selectedDay: LocalDate,
    onDaySelected: (LocalDate) -> Unit
) {
    val daysInMonth = YearMonth.of(currentYear, currentMonth).lengthOfMonth()
    val firstDayOfMonth = YearMonth.of(currentYear, currentMonth).atDay(1)
    //Adjust to monday was 0
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.value + 6) % 7

    val diasDeLaSemana = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(0.dp)
    ) {
        items(diasDeLaSemana.size) { index ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = diasDeLaSemana[index],
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
            }
        }

        items(startDayOfWeek) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Gray)
            )
        }

        items(daysInMonth) { day ->
            val dayOfMonth = day + 1
            val isSelected = selectedDay.dayOfMonth == dayOfMonth &&
                    selectedDay.monthValue == currentMonth &&
                    selectedDay.year == currentYear

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color.Gray)
                    .background(
                        color = if (isSelected) Color(0xFFECE6F0) else Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .clickable {
                        val selectedDate = LocalDate.of(currentYear, currentMonth, dayOfMonth)
                        onDaySelected(selectedDate)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$dayOfMonth",
                    style = TextStyle(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFFAE6BA4) else Color.Black,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun MonthCarouselPreview() {
    MonthCarousel(
        selectedMonth = 1,
        selectedYear = 2024,
        onMonthSelected = { month, year -> println("Month selected: $month, Year selected: $year") },
        onDaySelected = { day -> println("Day selected: $day") }
    )
}
