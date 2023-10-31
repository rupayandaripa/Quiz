package com.example.quiz.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.quiz.Data.CategoryPageData
import com.example.quiz.Data.CategoryPageType
import com.example.quiz.Data.CategoryPageUtils
import com.example.quiz.R

@Composable
fun CategoryPage(
    navController: NavHostController?,
    onClick: (CategoryPageType) -> Unit,
    categoryList: List<CategoryPageType>,
    context: Context

) {
    Column(
        modifier = Modifier.background(Color(0xFFFFE8C4)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.group_34),
            contentDescription = null,
            modifier = Modifier
                .padding(50.dp)
                .height(100.dp)
                .fillMaxWidth(),

        )

        Spacer(modifier = Modifier.height(30.dp))


            CategoryList(
                navController = navController,
                onClick = onClick,
                categoryList = categoryList
            )

    }



}

@Composable
fun CategoryList(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    categoryList: List<CategoryPageType>,
    onClick: (CategoryPageType) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(categoryList) {category ->
            Image(
                painter = painterResource(category.pictures),
                contentDescription = null,
                modifier = modifier
                    .clickable { onClick(category) }
                    .aspectRatio(1f)
                    .clip(RectangleShape)
                    .padding(16.dp),
                contentScale = ContentScale.FillBounds,

            )

        }
    }

}
@Composable
fun CategoryPageWithBurgerMenu(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    onClick: () -> Unit = {},
    categoryList: List<CategoryPageType>,
    context: Context
) {
    Box {
        CategoryPage(navController = navController, onClick = {}, categoryList = categoryList, context = context)

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
                .background(Color.DarkGray)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            // BurgerMenu content goes here
            BurgerMenu()
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    QuizTheme(useDarkTheme = false) {
        //CategoryPage()
    }
}

