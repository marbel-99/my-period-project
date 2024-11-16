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
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.myperiod.R

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3EDF7))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onCloseDrawer) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar menú",
                    tint = Color(0xFFAE6BA4)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Image(
                painter = painterResource(id = R.drawable.generic_avatar),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Perfil", style = MaterialTheme.typography.bodyLarge,  )
        SeparatorLine()
        Text(text = "Calendario", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        SeparatorLine()
        Text(text = "Duración del periodo", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(text = "7 días", style = MaterialTheme.typography.bodyLarge)
        SeparatorLine()
        Text(text = "Duración del ciclo", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(text = "28 días", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SeparatorLine(
    color: Color = Color.Gray,
    thickness: Dp = 0.3.dp,
    padding: Dp = 16.dp
) {
    Divider(
        color = color,
        thickness = thickness,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}

@Preview()
@Composable
fun DrawerContentPreview() {
    DrawerContent( onCloseDrawer = {} )
}