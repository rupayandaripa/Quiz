package com.example.quiz.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.R
import java.lang.reflect.Type

@Composable
fun BurgerMenu(
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.fillMaxHeight()) {
        Image(
            painter = painterResource(R.drawable.purprect),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(16.dp)) {
            Column(modifier = Modifier.background(Color(0xFF331255))) {

            }
            Spacer(modifier = Modifier.height(200.dp))


            Text(
                text = stringResource(id = R.string.profile),
                fontFamily = FontFamily(Font(R.font.dangrek_regular)),
                modifier = Modifier,
                fontSize = 10.sp,
                color = Color(0xFF331255)


            )

            Text(
                text = stringResource(id = R.string.leaderboard),
                fontFamily = FontFamily(Font(R.font.dangrek_regular)),
                modifier = Modifier,
                fontSize = 10.sp,
                color = Color(0xFF331255)
            )
            Text(
                text = stringResource(id = R.string.contactUs),
                fontFamily = FontFamily(Font(R.font.dangrek_regular)),
                modifier = Modifier,
                fontSize = 10.sp,
                color = Color(0xFF331255)
            )

            Text(
                text = stringResource(id = R.string.faqs),
                fontFamily = FontFamily(Font(R.font.dangrek_regular)),
                modifier = Modifier,
                fontSize = 10.sp,
                color = Color(0xFF331255)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp,end = 8.dp , top = 8.dp , bottom = 16.dp).align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = stringResource(R.string.about),
                fontSize = 5.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.termsAndConditions),
                fontSize = 5.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.privacy),
                fontSize = 5.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Preview
@Composable
fun BurgerMenuPreview() {
    QuizTheme(useDarkTheme = false) {
        BurgerMenu()
    }
}