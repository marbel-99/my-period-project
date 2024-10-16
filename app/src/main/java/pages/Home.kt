package pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.myperiod.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "My Period",
                        fontFamily = FontFamily.Default, // Replace with Roboto if available
                        fontSize = 32.sp,
                        color = Color(0xFFAE6BA4)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu click */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color(0xFFAE6BA4)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White // Header background color
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4)) // Background color of the content area
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // "Próximo período" title
            Text(
                text = "Próximo período",
                style = MaterialTheme.typography.bodyLarge, // Assuming bodyStrong is equivalent to bodyLarge
                color = Color(0xFF49454F),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Title in AE6BA4 color
            Text(
                text = "Faltan 16 días", // Replace with your actual title
                style = MaterialTheme.typography.titleLarge, // Assuming titlePage is equivalent to titleLarge
                color = Color(0xFFAE6BA4),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Title in Headline medium/font
            Text(
                text = "25 octubre", // Replace with your actual title
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp,
                color = Color(0xFF49454F),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Button with pause icon
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .width(210.dp)
                    .height(82.dp)
                    .fillMaxWidth()
                    .padding(16.dp),

                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pause_circle_24),
                    contentDescription = "Pause",
                    tint = Color(0xFF65558F),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Texto del botón", // Replace with your actual button text
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 14.sp,
                    color = Color(0xFF65558F)
                )
            }



            // Button with add_period icon
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .width(100.dp)
                    .height(82.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_period),
                    contentDescription = "Add Period",
                    tint = Color(0xFFAE6BA4), // You can adjust the icon color
                    modifier = Modifier.size(500.dp) // Adjust icon size as needed
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}
