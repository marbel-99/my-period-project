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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.myperiod.R
import components.CyclePhasesCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    var drawerState by remember { mutableStateOf(DrawerValue.Closed) }
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
                    IconButton(onClick ={ drawerState = DrawerValue.Open }) {
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

            Spacer(modifier = Modifier.padding(16.dp))
            // "Próximo período" title
            Text(
                text = "Próximo período",
                style = MaterialTheme.typography.bodyLarge, // Assuming bodyStrong is equivalent to bodyLarge
                color = Color(0xFF49454F),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))

            // Title in AE6BA4 color
            Text(
                text = "Faltan 16 días", // Replace with your actual title
                style = MaterialTheme.typography.titleLarge, // Assuming titlePage is equivalent to titleLarge
                color = Color(0xFFAE6BA4),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.padding(8.dp))

            // Title in Headline medium/font
            Text(
                text = "25 octubre", // Replace with your actual title
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp,
                color = Color(0xFF49454F),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.padding(26.dp))

            // Button with pause icon
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .width(190.dp)
                    .height(52.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 3.dp,
                        shape = CircleShape,
                        clip = false
                    ),
                   // Apply clip to the Button composable
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
            ){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pause_circle_24),
                    contentDescription = "Pause",
                    tint = Color(0xFF65558F),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Fin del período", // Replace with your actual button text
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 14.sp,
                    color = Color(0xFF65558F)
                )
            }

            Spacer(modifier = Modifier.padding(26.dp))

            CyclePhasesCard()

            Spacer(modifier = Modifier.padding(16.dp))

            // Button with add_period icon
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .width(100.dp)
                    .height(82.dp)
                    .shadow(
                        elevation = 3.dp,
                        shape = CircleShape, // Use CircleShape for circular button
                        clip = false // Allow shadow to extend beyond button bounds
                    ), // Add shadow with elevation 3.dp
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
