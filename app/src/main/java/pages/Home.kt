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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.myperiod.FirebaseAuthentication
import com.project.myperiod.FirebaseDatabase
import com.project.myperiod.R
import com.project.myperiod.ui.theme.MyPeriodTheme
import components.CyclePhasesCard
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    val firebaseAuthentication = FirebaseAuthentication()
    val firebaseDatabase = FirebaseDatabase()

    var userId by remember { mutableStateOf("") }
    var isInPeriod by remember { mutableStateOf(false) }
    var periodDaysText by remember { mutableStateOf("") }
    var statusPeriodText by remember { mutableStateOf("") }
    var dateEndPeriod by remember { mutableStateOf("") }
    var lastPeriodStartDate by remember { mutableStateOf("") }


    fun navigateToLogin() {
        navController.navigate("login") {
            popUpTo(route = "home") { inclusive = true } // Remove splash screen from back stack
        }
    }


    fun setStatusPeriodText() {
        // Verifica si hay un período activo
        if (isInPeriod) {
            statusPeriodText = "Estás en periodo"
        } else {
            statusPeriodText = "Próximo periodo"
        }

    }

    fun setIsInPeriod() {
        firebaseAuthentication.getCurrentUserUid()?.let { userId ->
            firebaseDatabase.getLastPeriodDate(userId) { lastPeriod ->
                if (lastPeriod != null) {
                    if (lastPeriod.end_date.isNullOrEmpty()) {
                        // Si "end_date" es nulo o vacío, estamos en período
                        isInPeriod = true
                    } else {
                        // Si "end_date" tiene valor, no estamos en período
                        isInPeriod = false
                    }
                } else {
                    // Si no hay ningún período registrado, no estamos en período
                    isInPeriod = false
                }
                setStatusPeriodText()

            }
        } ?: run {
            // Si el usuario no está autenticado, se establece como false
            isInPeriod = false
            setStatusPeriodText()

        }
    }





    fun setUserId() {
        userId = firebaseAuthentication.getCurrentUserUid().toString()
    }



    fun parseDate(dateStr: String?): Date? {
        if (dateStr.isNullOrEmpty()) return null

        val formats = arrayOf(
            "yyyy-MM-dd",
            "dd/MM/yyyy"
            // Agrega otros formatos si es necesario
        )

        for (formatStr in formats) {
            try {
                val format = SimpleDateFormat(formatStr, Locale.getDefault())
                return format.parse(dateStr)
            } catch (e: ParseException) {
                // Continuar con el siguiente formato
            }
        }

        return null
    }

    fun addDaysToDate(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.time
    }



    fun setPeriodDaysText() {
        firebaseDatabase.fetchFrequencyDays(userId) { frequencyDays ->
            firebaseDatabase.fetchInitialPeriodDate(userId) { initialDateStr ->
                firebaseDatabase.getLastPeriodDate(userId) { lastPeriod ->
                    val today = Calendar.getInstance().time

                    if (lastPeriod == null) {
                        // Caso 1: No hay registros en "periods", usar "period_initial_registered"
                        if (initialDateStr != null) {
                            val initialDate = parseDate(initialDateStr)
                            if (initialDate != null) {
                                val nextPeriodDate = addDaysToDate(initialDate, frequencyDays)
                                val diffInDays = ((nextPeriodDate.time - today.time) / (1000 * 60 * 60 * 24)).toInt()
                                val resultText = if (diffInDays > 0) {
                                    "Faltan $diffInDays días"
                                } else {
                                    "Tarde ${-diffInDays} días"
                                }
                                periodDaysText = resultText
                            } else {
                                periodDaysText = "Error al parsear la fecha inicial"
                            }
                        } else {
                            periodDaysText = "No hay fecha inicial registrada"
                        }
                    } else {
                        // Caso 2: Hay registros en "periods", usar el último registro
                        if (!lastPeriod.end_date.isNullOrEmpty()) {
                            // Subcaso A: "end_date" no es nulo o vacío
                            val endDate = parseDate(lastPeriod.end_date)
                            if (endDate != null) {
                                val nextPeriodDate = addDaysToDate(endDate, frequencyDays)
                                val diffInDays = ((nextPeriodDate.time - today.time) / (1000 * 60 * 60 * 24)).toInt()
                                val resultText = if (diffInDays > 0) {
                                    "Faltan $diffInDays días"
                                } else {
                                    "Tarde ${-diffInDays} días"
                                }
                                periodDaysText = resultText
                            } else {
                                periodDaysText = "Error al parsear 'end_date' del último período"
                            }
                        } else {
                            // Subcaso B: "end_date" es nulo o vacío, período en curso
                            val startDate = parseDate(lastPeriod.start_date)
                            if (startDate != null) {
                                val diffInDays = ((today.time - startDate.time) / (1000 * 60 * 60 * 24)).toInt()
                                periodDaysText = "$diffInDays días"
                            } else {
                                periodDaysText = "Error al parsear 'start_date' del período actual"
                            }
                        }
                    }
                }
            }
        }
    }

    fun formatDateToDayMonth(date: Date): String {
        val format = SimpleDateFormat("dd 'de' MMMM", Locale("es", "ES"))
        return format.format(date)
    }

    fun setDateEndPeriod() {
        firebaseDatabase.fetchFrequencyDays(userId) { frequencyDays ->
            firebaseDatabase.fetchAveragePeriodDays(userId) { averagePeriodDays ->
                firebaseDatabase.fetchInitialPeriodDate(userId) { initialDateStr ->
                    firebaseDatabase.getLastPeriodDate(userId) { lastPeriod ->
                        val today = Calendar.getInstance().time

                        if (isInPeriod) {
                            // Caso 3: Período activo
                            val startDate = parseDate(lastPeriod?.start_date)
                            if (startDate != null) {
                                val expectedEndDate = addDaysToDate(startDate, averagePeriodDays)
                                val formattedDate = formatDateToDayMonth(expectedEndDate)
                                dateEndPeriod = "Tu periodo debería finalizar el $formattedDate"
                            } else {
                                dateEndPeriod = "Error al obtener la fecha de inicio del período actual"
                            }
                        } else {
                            // Caso 1 y 2: Sin período activo
                            var referenceDate: Date? = null
                            if (lastPeriod != null && !lastPeriod.end_date.isNullOrEmpty()) {
                                // Usar el "end_date" del último período
                                referenceDate = parseDate(lastPeriod.end_date)
                            } else if (initialDateStr != null) {
                                // Usar la fecha inicial registrada
                                referenceDate = parseDate(initialDateStr)
                            }

                            if (referenceDate != null) {
                                val nextExpectedPeriodDate = addDaysToDate(referenceDate, frequencyDays)
                                if (today.after(nextExpectedPeriodDate)) {
                                    // Caso 2: El período se está retrasando
                                    dateEndPeriod = "Tu periodo se está retrasando"
                                } else {
                                    // Caso 1: Mostrar la fecha esperada
                                    val formattedDate = formatDateToDayMonth(nextExpectedPeriodDate)
                                    dateEndPeriod = formattedDate
                                }
                            } else {
                                dateEndPeriod = "No hay fecha de referencia disponible"
                            }
                        }
                    }
                }
            }
        }
    }

    // Función para obtener la start_date del último período
    fun fetchLastPeriodStartDate() {
        firebaseAuthentication.getCurrentUserUid()?.let { userId ->
            firebaseDatabase.getLastPeriodDate(userId) { lastPeriod ->
                if (lastPeriod != null) {
                    lastPeriodStartDate = lastPeriod.start_date ?: ""
                } else {
                    lastPeriodStartDate = ""
                }
            }
        }
    }




    // LaunchedEffect to call calculateIsInPeriod when Home is displayed
    LaunchedEffect(Unit) {
        setUserId()
        setIsInPeriod()
        setPeriodDaysText()
        setDateEndPeriod()
        fetchLastPeriodStartDate()
    }





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
                actions = {
                    IconButton(
                        onClick = {
                            firebaseAuthentication.logout()
                            navigateToLogin()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "Logout",
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
                text = statusPeriodText,
                style = MaterialTheme.typography.bodyLarge, // Assuming bodyStrong is equivalent to bodyLarge
                color = Color(0xFF49454F),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .testTag("statusPeriodText")

            )
            Spacer(modifier = Modifier.padding(8.dp))


            // Title in AE6BA4 color
            Text(
                text = periodDaysText, // Replace with your actual title
                style = MaterialTheme.typography.titleLarge, // Assuming titlePage is equivalent to titleLarge
                color = Color(0xFFAE6BA4),
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .testTag("periodDaysText")

            )
            Spacer(modifier = Modifier.padding(8.dp))

            // Title in Headline medium/font
            Text(
                text = dateEndPeriod, // Replace with your actual title
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp,
                color = Color(0xFF49454F),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .testTag("dateEndPeriod")
            )

            Spacer(modifier = Modifier.padding(36.dp))


            CyclePhasesCard(startDate = lastPeriodStartDate)

            Spacer(modifier = Modifier.padding(46.dp))


            if (isInPeriod) {
                // Button with pause icon
                Button(
                    onClick = {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val todayDate = dateFormat.format(Date())

                        firebaseAuthentication.getCurrentUserUid()?.let { userId ->
                            firebaseDatabase.setLastPeriodEndDate(userId, todayDate) { success ->
                                if (success) {
                                    // Actualiza el estado local
                                    isInPeriod = false
                                    setStatusPeriodText()
                                    setPeriodDaysText()
                                    setDateEndPeriod()
                                    fetchLastPeriodStartDate()
                                } else {
                                    // Maneja el error (por ejemplo, muestra un mensaje al usuario)
                                    println("Error al finalizar el período")
                                }
                            }
                        } ?: run {
                            // El usuario no está autenticado
                            println("Usuario no autenticado")
                        }
                    },
                    modifier = Modifier
                        .width(190.dp)
                        .height(52.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 3.dp,
                            shape = CircleShape,
                            clip = false
                        )
                        .testTag("finishPeriod"),
                    // Apply clip to the Button composable
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
                        text = "Fin del período", // Replace with your actual button text
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 14.sp,
                        color = Color(0xFF65558F)
                    )
                }
            } else {

                Button(
                    onClick = {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val todayDate = dateFormat.format(Date())

                        firebaseAuthentication.getCurrentUserUid()?.let { userId ->
                            firebaseDatabase.addNewPeriod(userId, todayDate) { success ->
                                if (success) {
                                    // Actualiza el estado local
                                    isInPeriod = true
                                    setStatusPeriodText()
                                    setPeriodDaysText()
                                    setDateEndPeriod()
                                    fetchLastPeriodStartDate()
                                } else {
                                    // Maneja el error
                                    println("Error al iniciar el período")
                                }
                            }
                        } ?: run {
                            // El usuario no está autenticado
                            println("Usuario no autenticado")
                        }
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .height(82.dp)
                        .shadow( // Add shadow with elevation 3.dp
                            elevation = 3.dp,
                            shape = CircleShape, // Use CircleShape for circular button
                            clip = false // Allow shadow to extend beyond button bounds
                        )
                        .testTag("startPeriod"),
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
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {

    MyPeriodTheme {
        Home(
            navController = rememberNavController()
        )
    }
}
