package com.example.quiz.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.quiz.R

@Composable
fun SubmissionPrompt(
    modifier: Modifier = Modifier,
    cancel: () -> Unit,
    submit: () -> Unit
) {
    Box(modifier = modifier.size(200.dp)){
        Image(
            painter = painterResource(R.drawable.prompt),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        Row(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp, start = 10.dp , end = 10.dp)) {
            Image(
                painter = painterResource(R.drawable.cancel),
                contentDescription = null,
                modifier = modifier.clickable {cancel()}.size(70.dp).fillMaxSize()
            )

             Spacer(modifier = Modifier.width(50.dp))

            Image(
                painter = painterResource(R.drawable.submit_2),
                contentDescription = null,
                modifier = Modifier.clickable {submit()}.size(70.dp).fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun SubmissionPreview() {
    SubmissionPrompt(cancel = {} , submit = {})
}