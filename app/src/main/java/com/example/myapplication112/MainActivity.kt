package com.example.myapplication112

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication112.ui.theme.MyApplication112Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication112Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OnboardingScreens(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun OnboardingScreens(innerPadding: PaddingValues, modifier: Modifier = Modifier) {
    var currentPage by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(5f) }

    LaunchedEffect(currentPage) {
        if (currentPage < 4) {
            while (timeLeft > 0) {
                delay(100)
                timeLeft -= 0.1f
            }
            currentPage++
            timeLeft = 5f
        }
    }

    val onNextClick = {
        if (currentPage < 4) {
            currentPage++
            timeLeft = 5f
        }
    }

    val onSkipClick = {
        currentPage = 4
        timeLeft = 0f
    }

    if (currentPage < 4) {
        OnboardingPage(
            image = painterResource(id = when (currentPage) {
                0 -> R.drawable.img1
                1 -> R.drawable.img2
                2 -> R.drawable.img3
                3 -> R.drawable.img4
                else -> R.drawable.img1
            }),
            mainText = when (currentPage) {
                0 -> "Your first car without                      a driver's license"
                1 -> "Always there: more than                     1000 cars in Tbilisi"
                2 -> "Do not pay for parking, maintenance and gasoline"
                3 -> "29 car models: from Skoda Octavia to Porsche 911"
                else -> ""
            },
            secondaryText = when (currentPage) {
                0 -> "Goes to meet people who just got    their license"
                1 -> "Our company is a leader by the number of cars in the fleet"
                2 -> "We will pay for you, all expenses related to the car"
                3 -> "Choose between regular car models or exclusive ones"
                else -> ""
            },
            backgroundColor = when (currentPage) {
                0 -> Color(0xFFF0CF69)
                1 -> Color(0xFFB7ABFD)
                2 -> Color(0xFFEFB491)
                3 -> Color(0xFF95B6FF)
                else -> Color.White
            },
            onNextClick = onNextClick,
            onSkipClick = onSkipClick,
            timeLeft = timeLeft,
            nextTextColor = when (currentPage) {
                0 -> Color(0xFFF0CF69)
                1 -> Color(0xFFB7ABFD)
                2 -> Color(0xFFEFB491)
                3 -> Color(0xFF95B6FF)
                else -> Color.White
            },
            currentPage = currentPage,
            modifier = Modifier.padding(innerPadding)
        )
    } else {
        FinalPage(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun OnboardingPage(
    image: Painter,
    mainText: String,
    secondaryText: String,
    backgroundColor: Color,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    timeLeft: Float,
    nextTextColor: Color,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(83.dp))
            Text(
                text = mainText,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = if (currentPage == 0) FontWeight.Normal else FontWeight.Bold,
                modifier = Modifier
                    .width(400.dp)
                    .height(64.dp)
                    .padding(start = 24.dp)
            )
            Text(
                text = secondaryText,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .width(327.dp)
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(38.dp))
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .width(375.dp)
                    .height(428.dp)
                    .fillMaxWidth() // Занимаем максимально доступную ширину
                    .align(
                        when (currentPage) {
                            0, 3 -> Alignment.Start // Выравниваем img1 и img4 по правому краю
                            1, 2 -> Alignment.End // Выравниваем img2 и img3 по левому краю
                            else -> Alignment.Start // Для всех остальных случаев
                        }
                    )
            )



        }
        Image(
            painter = painterResource(id = getNavigationImage(currentPage)),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 108.dp)
                .padding(start = 24.dp)
        )
        Text(
            text = "Skip",
            color = Color.White.copy(alpha = 0.7f),
            fontFamily = FontFamily.SansSerif,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 74.dp)
                .background(Color(0xFFFFFFB2).copy(alpha = 0f))
                .clickable { onSkipClick() }
        )
        CircularProgressButton(
            onNextClick = onNextClick,
            timeLeft = timeLeft,
            nextTextColor = nextTextColor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 58.dp)
        )
    }
}

fun getNavigationImage(currentPage: Int): Int {
    return when (currentPage) {
        0 -> R.drawable.img1_nav
        1 -> R.drawable.img2_nav
        2 -> R.drawable.img3_nav
        3 -> R.drawable.img4_nav
        else -> throw IllegalArgumentException("Invalid page number: $currentPage")
    }
}

@Composable
fun CircularProgressButton(onNextClick: () -> Unit, timeLeft: Float, nextTextColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(58.dp)
            .clickable { onNextClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(58.dp)) {
            drawCircle(
                color = Color.White,
                radius = size.minDimension / 2,
                style = Stroke(width = 4.dp.toPx())
            )
            drawArc(
                color = Color.Gray,
                startAngle = -90f,
                sweepAngle = 360f * (1 - timeLeft / 5),
                useCenter = false,
                style = Stroke(width = 4.dp.toPx())
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.White, shape = androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ">",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = nextTextColor
            )
        }
    }
}

@Composable
fun FinalPage(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF95B6FF))
    ) {
        Text(
            text = "You are a clever person!",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    MyApplication112Theme {
        OnboardingScreens(innerPadding = PaddingValues(0.dp))
    }
}
