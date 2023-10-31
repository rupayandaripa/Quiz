package com.example.quiz.ui.theme


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter.State.Empty.painter

import com.example.quiz.Data.LandingPageData
import com.example.quiz.R
import kotlin.math.roundToInt



@Composable
fun LandingPageContent(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    onClick: () -> Unit = {}
) {

    SwipeableImageList(itemList = LandingPageData.gameModeTypes , onClick = onClick , modifier = modifier)

}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableImageList(
    itemList: List<Int>,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val pagerState = rememberPagerState()
    var previousPage by remember { mutableStateOf(0) }

   Column(
       modifier = modifier.background(Color(0xFFFFE8C4))
   ) {
       Image(
           painter = painterResource(R.drawable.group_35),
           contentDescription = null,
           modifier = Modifier
               .fillMaxWidth()
               .padding(50.dp)
               .height(100.dp),
           contentScale = ContentScale.FillBounds,

           )

       LaunchedEffect(pagerState.currentPage) {
           if (pagerState.currentPage != previousPage) {
               previousPage = pagerState.currentPage
           }
       }

       Box(
           modifier = Modifier
               .fillMaxSize()
       ) {

           HorizontalPager(
               state = pagerState,
               pageCount = itemList.size,
               modifier = modifier.animateContentSize()
           ) { page ->
               val parallaxOffset = ((pagerState.currentPage - page) * 100).dp // Adjust the parallax offset as needed
               Image(
                   painter = painterResource(itemList[page]),
                   contentDescription = null,
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(16.dp)
                       .offset(x = parallaxOffset)
                       .clickable { onClick() }
               )

               Spacer(modifier = modifier.width(8.dp))
           }
       }
   }
}
@Composable
fun LandingPageWithBurgerMenu(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    onClick: () -> Unit = {}
) {
    Box {
        LandingPageContent(navController = navController, modifier = modifier)

        // Dark semi-transparent background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Adjust the alpha to control darkness
                .clickable { onClick() }
        )

        // Burger menu
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f) // Set the width to one-third of the screen width
                .fillMaxHeight() // Cover the entire height
                .background(Color.DarkGray).align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            // BurgerMenu content goes here
            BurgerMenu()
        }
    }
}

@Preview
@Composable
fun LandingPagePreview() {
    QuizTheme(useDarkTheme = false) {
        LandingPageWithBurgerMenu(navController = null)
    }
}