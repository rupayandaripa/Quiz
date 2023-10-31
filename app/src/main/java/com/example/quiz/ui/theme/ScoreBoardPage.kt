package com.example.quiz.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.R

@Composable
fun ScorePage(
    modifier: Modifier = Modifier,
    progress: Float
) {
    Box(
        modifier = modifier
            .background(Color(0xFFFFE8C4))
            .fillMaxSize()
    )
     {
        Card(
            modifier = modifier
                .shadow(55.dp)
                .width(300.dp)
                .height(350.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFA407)),

        ) {

            Column {

                Text(
                    text = "${(progress*10).toInt()}/10",
                    fontSize = 50.sp,
                    fontFamily = FontFamily(Font(R.font.dangrek_regular)),
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                LinearProgressIndicator(
                    progress = progress,
                    modifier = modifier.padding(36.dp).height(15.dp),
                    trackColor = Color(0xFFB969C7),
                    color = Color(0xFF28072E),
                    strokeCap = StrokeCap.Round

                )

                Spacer(modifier.height(20.dp))

                Text(
                    text = "You Scored ${(progress*10).toInt()} out of 10",
                    fontFamily = FontFamily(Font(R.font.dangrek_regular)),
                    modifier = modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }

        }
    }
}

@Preview
@Composable
fun ScorePageView() {
    QuizTheme {
        ScorePage(
            progress = 0.5f
        )
    }
}

