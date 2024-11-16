package pages

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import components.CyclePhasesCard
import components.DrawerContent
import kotlinx.coroutines.launch
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
            popUpTo(route = "home") { inclusive = true }
        }
    }


    fun setStatusPeriodText() {

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

                        isInPeriod = true
                    } else {

                        isInPeriod = false
                    }
                } else {

                    isInPeriod = false
                }
                setStatusPeriodText()

            }
        } ?: run {

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

        )

        for (formatStr in formats) {
            try {
                val format = SimpleDateFormat(formatStr, Locale.getDefault())
                return format.parse(dateStr)
            } catch (e: ParseException) {

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

                        // Case 1: No records in "periods", use "period_initial_registered"
                        if (initialDateStr != null) {
                            val initialDate = parseDate(initialDateStr)
                            if (initialDate != null) {
                                val nextPeriodDate = addDaysToDate(initialDate, frequencyDays)
                                val diffInDays =
                                    ((nextPeriodDate.time - today.time) / (1000 * 60 * 60 * 24)).toInt()
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
                        // Case 2: There are records in "periods", use the last record
                        if (!lastPeriod.end_date.isNullOrEmpty()) {

                            // Subcase A: "end_date" is not null or empty
                            val endDate = parseDate(lastPeriod.end_date)
                            if (endDate != null) {
                                val nextPeriodDate = addDaysToDate(endDate, frequencyDays)
                                val diffInDays =
                                    ((nextPeriodDate.time - today.time) / (1000 * 60 * 60 * 24)).toInt()
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
                            // Subcase B: "end_date" is null or empty, current period
                            val startDate = parseDate(lastPeriod.start_date)
                            if (startDate != null) {
                                val diffInDays =
                                    ((today.time - startDate.time) / (1000 * 60 * 60 * 24)).toInt()
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
                            // Case 3: Active period
                            val startDate = parseDate(lastPeriod?.start_date)
                            if (startDate != null) {
                                val expectedEndDate = addDaysToDate(startDate, averagePeriodDays)
                                val formattedDate = formatDateToDayMonth(expectedEndDate)
                                dateEndPeriod = "Tu periodo debería finalizar el $formattedDate"
                            } else {
                                dateEndPeriod =
                                    "Error al obtener la fecha de inicio del período actual"
                            }
                        } else {
                            // Case 1 and 2: No active period
                            var referenceDate: Date? = null
                            if (lastPeriod != null && !lastPeriod.end_date.isNullOrEmpty()) {
                                // Use the "end_date" of the last period
                                referenceDate = parseDate(lastPeriod.end_date)
                            } else if (initialDateStr != null) {
                                // Use the registered initial date
                                referenceDate = parseDate(initialDateStr)
                            }

                            if (referenceDate != null) {
                                val nextExpectedPeriodDate =
                                    addDaysToDate(referenceDate, frequencyDays)
                                if (today.after(nextExpectedPeriodDate)) {

                                    // Case 2: Period is running late
                                    dateEndPeriod = "Tu periodo se está retrasando"
                                } else {
                                    // Case 1: Show the expected date
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

    // Function to get the start_date of the last period
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


    // LaunchedEffect to call when Home is displayed
    LaunchedEffect(Unit) {
        setUserId()
        setIsInPeriod()
        setPeriodDaysText()
        setDateEndPeriod()
        fetchLastPeriodStartDate()
    }


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            if (drawerState.isOpen) {
                DrawerContent(
                    onCloseDrawer = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "My Period",
                            fontFamily = FontFamily.Default,
                            fontSize = 32.sp,
                            color = Color(0xFFAE6BA4)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
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
                        containerColor = Color.White
                    )
                )
            },
        )

        { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF4F4F4))
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(16.dp))
                // "Próximo período" title
                Text(
                    text = statusPeriodText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF49454F),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .testTag("statusPeriodText")

                )
                Spacer(modifier = Modifier.padding(8.dp))


                Text(
                    text = periodDaysText,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFAE6BA4),
                    textAlign = TextAlign.Center,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .testTag("periodDaysText")

                )
                Spacer(modifier = Modifier.padding(8.dp))


                Text(
                    text = dateEndPeriod,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 28.sp,
                    color = Color(0xFF49454F),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .testTag("dateEndPeriod")
                        .width(350.dp)
                )

                Spacer(modifier = Modifier.padding(36.dp))


                CyclePhasesCard(startDate = lastPeriodStartDate)

                Spacer(modifier = Modifier.padding(46.dp))


                if (isInPeriod) {

                    Button(
                        onClick = {
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val todayDate = dateFormat.format(Date())

                            firebaseAuthentication.getCurrentUserUid()?.let { userId ->
                                firebaseDatabase.setLastPeriodEndDate(
                                    userId,
                                    todayDate
                                ) { success ->
                                    if (success) {
                                        // Actualiza el estado local
                                        isInPeriod = false
                                        setStatusPeriodText()
                                        setPeriodDaysText()
                                        setDateEndPeriod()
                                        fetchLastPeriodStartDate()
                                    } else {

                                        println("Error al finalizar el período")
                                    }
                                }
                            } ?: run {

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
                            text = "Fin del período",
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

                                        isInPeriod = true
                                        setStatusPeriodText()
                                        setPeriodDaysText()
                                        setDateEndPeriod()
                                        fetchLastPeriodStartDate()
                                    } else {

                                        println("Error al iniciar el período")
                                    }
                                }
                            } ?: run {

                                println("Usuario no autenticado")
                            }
                        },
                        modifier = Modifier
                            .width(100.dp)
                            .height(82.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = CircleShape,
                                clip = false
                            )
                            .testTag("startPeriod"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_period),
                            contentDescription = "Add Period",
                            tint = Color(0xFFAE6BA4),
                            modifier = Modifier.size(500.dp)
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomePreview() {

        Home(
            navController = rememberNavController()
        )
}
