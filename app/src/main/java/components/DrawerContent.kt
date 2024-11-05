package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.myperiod.R

@Composable
fun DrawerContent(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3EDF7))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.generic_avatar),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "userName",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Calendario", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Duración del periodo", style = MaterialTheme.typography.bodyMedium)
        Text(text = "7 días", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Duración del ciclo", style = MaterialTheme.typography.bodyMedium)
        Text(text = "25 días", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview()
@Composable
fun DrawerContentPreview() {
    DrawerContent(userName = String())
}