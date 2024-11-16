package components

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.project.myperiod.R
import java.time.LocalDate
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderCard(    selectedDay: LocalDate,
                     selectedMonth: Int,
                     selectedYear: Int) {
    val context = LocalContext.current
    var isReminderEnabled by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFEF7FF))
            .shadow(
                elevation = 4.dp,
                spotColor = Color.Black,
                ambientColor = Color.Black,
                shape = RoundedCornerShape(6.dp),
                clip = false
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEADDFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter =  painterResource(id = R.drawable.baseline_notification_add_24),
                    contentDescription = "Add Reminder"
                )

            }


            Text(
                text = "Recuérdame",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black // Text color
            )


            Switch(
                checked = isReminderEnabled,
                onCheckedChange = { isChecked ->
                    isReminderEnabled = isChecked
                    if (isChecked) {
                        // Connect to the device's calendar and activate a reminder
                        val calendarIntent = createCalendarIntent(context, selectedDay, selectedMonth, selectedYear)
                        ContextCompat.startActivity(context, calendarIntent, null)
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            )
        }
    }
}

fun createCalendarIntent(context: Context, selectedDay: LocalDate, selectedMonth: Int, selectedYear: Int): Intent {
    val startMillis = selectedDay.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val endMillis = selectedDay.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
        .putExtra(CalendarContract.Events.TITLE, "Recuérdame") // Reminder title
        .putExtra(CalendarContract.Events.DESCRIPTION, "Recordatorio para el día seleccionado") // Reminder description
        .putExtra(CalendarContract.Events.EVENT_LOCATION, "Ubicación del recordatorio") // Optional location
        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY) // Optional availability

    return intent
}

@Preview(showBackground = true)
@Composable
fun ReminderCardPreview() {
    val context = LocalContext.current // Get the context

            ReminderCard(
                selectedDay = LocalDate.now(),
                selectedMonth = LocalDate.now().monthValue,
                selectedYear = LocalDate.now().year
            )
        }
