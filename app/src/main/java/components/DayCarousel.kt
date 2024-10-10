package components

import androidx.activity.result.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DayCarousel(selectedDays: Int, onDaysSelected: (Int) -> Unit) {
    val daysList = (1..31).toList() // Range of days
    val pagerState = rememberPagerState( )
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedDays) {
        val index = daysList.indexOf(selectedDays)
        if (index != -1) {
            pagerState.scrollToPage(index)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // Adjust height as needed
    ) {

        HorizontalPager(
            count = daysList.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Row ( // Use a Row to display three items

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,

                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentSize()
            )

            {
                // Previous Day (without background)
                if (page > 0) {
                    Text(
                        text = "${daysList[page - 1]}" +"\n \n Días",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF878787) // Adjust color as needed
                        )

                    )

                }

                Spacer(modifier = Modifier.padding( 16.dp))

                // Selected Day (with background)
                val currentDays = daysList[page]
                val isSelected = currentDays == selectedDays
                Text(
                    text = "$currentDays" + "\n \n Días",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = if (isSelected) Color.Black else Color(0xFF878787)
                    ),
                    modifier = if (isSelected) {
                        Modifier
                            .background(
                                color = Color(0xFFECE6F0),
                                shape = RoundedCornerShape(46.dp)
                            )
                            .padding(20.dp)
                    } else {
                        Modifier
                    }
                )
                Spacer(modifier = Modifier.padding( 16.dp))

                // Next Day (without background)
                if (page < daysList.size - 1) {
                    Text(
                        text = "${daysList[page + 1]}" +"\n \n Días",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF878787) // Adjust color as needed
                        )
                    )
                }
            }
        }

        // Arrows for Navigation
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            IconButton(
                onClick = {
                    scope.launch { // Launch a coroutine
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                enabled = pagerState.currentPage > 0
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous",
                    tint = Color(0xFF65558F)
                )
            }

            IconButton(
                onClick = {
                    scope.launch { // Launch a coroutine
                        if (pagerState.currentPage < daysList.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                enabled = pagerState.currentPage < daysList.size - 1
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = Color(0xFF65558F)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayCarouselPreview() {
    DayCarousel(selectedDays = 28, onDaysSelected = {})
}