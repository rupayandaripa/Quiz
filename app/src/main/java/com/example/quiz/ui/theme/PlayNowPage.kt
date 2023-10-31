package com.example.quiz.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz.Data.CategoryPageType
import com.example.quiz.Data.CategoryPageUtils
import com.example.quiz.Data.PlayNowPageData
import com.example.quiz.R

@Composable
fun PlayNowScreen(
    category: CategoryPageType,
    modifier: Modifier = Modifier,
    playNow: (CategoryPageType) -> Unit
) {
    
    val image = when(category.type) {
        CategoryPageUtils.SPORTS -> R.drawable.sports
        CategoryPageUtils.NATURE -> R.drawable.nature
        CategoryPageUtils.SCIENCE -> R.drawable.group_11
        CategoryPageUtils.MOVIE -> R.drawable.movie
        CategoryPageUtils.ANIMALS -> R.drawable.animal
        else -> R.drawable.history
    }
    Box(modifier = modifier.background(Color(0xFFFFE8C4)).fillMaxSize()) {
        Card(modifier = modifier.align(Alignment.Center).size(500.dp).padding(start = 20.dp , end = 20.dp)) {
            Box(modifier = modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    modifier = modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource(R.drawable.group_26),
                    contentDescription = null,
                    modifier = modifier.clickable {playNow(category) }.align(Alignment.BottomCenter).padding(bottom = 30.dp).width(200.dp).fillMaxWidth().height(50.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}



@Preview
@Composable
fun PlayNowScreenPreview() {
    //PlayNowScreen(category = CategoryPageUtils.SCIENCE)
}