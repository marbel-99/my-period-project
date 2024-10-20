package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.myperiod.R


@Composable
fun CyclePhasesCard() {
    Card(
        modifier = Modifier
            .width(367.dp)
            .height(195.dp),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp), // Add rounded corners
        border = BorderStroke(1.dp, Color(0xFFCAC4D0))

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Fases de ciclo",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                PhaseCard(
                    painter = painterResource(id = R.drawable.fertility),
                    title = "Período fértil",
                    dateRange = "7-10 oct."
                )

                PhaseCard(
                    painter = painterResource(id = R.drawable.ovulation),
                    title = "Ovulación",
                    dateRange = "14-17 oct."
                )
            }
        }
    }
}

@Composable
fun PhaseCard(painter: Painter, title: String, dateRange: String) {
    Card(
        modifier = Modifier
            .width(174.dp)
            .height(124.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF7FF)),
        shape = RoundedCornerShape(16.dp), // Add rounded corners
        border = BorderStroke(1.dp, Color(0xFFCAC4D0))
    ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
           Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp, 62.dp) // Adjust icon size as needed
                    .align(Alignment.Start)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = dateRange,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CyclePhasesCardPreview() {
    CyclePhasesCard()
}