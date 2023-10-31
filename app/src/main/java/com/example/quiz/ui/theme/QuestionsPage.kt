package com.example.quiz.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.quiz.Data.CategoryPageUtils
import com.example.quiz.Data.Question
import com.example.quiz.Data.ShuffledOptions
import com.example.quiz.R
import com.example.quiz.model.QuizUiState
import com.example.quiz.model.QuizViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Integer.min
import java.nio.file.WatchEvent


@Composable
fun QuizScreenWithPrompt(
    quizUiState: QuizUiState,
    viewModel: QuizViewModel,
    goToScorePage: () -> Unit,
    onTimerFinished: () -> Unit
) {
    Box() {
        QuizScreen(quizUiState = quizUiState, viewModel = viewModel , onTimerFinished = onTimerFinished)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Adjust the alpha to control darkness
        )

        // Burger menu
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp),
            contentAlignment = Alignment.Center
        ) {
            // BurgerMenu content goes here
            SubmissionPrompt(
                cancel = {viewModel.submissionPromptOpen = false},
                submit = {viewModel.getScore(); Log.d("score" , "${viewModel.getScore()}") ; goToScorePage() },
            )

        }
    }
}

@Composable
fun QuizScreen(
    quizUiState: QuizUiState,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel,
    onTimerFinished: () -> Unit
) {
    val count = viewModel.count
    LaunchedEffect(count) {

    }


    when(quizUiState) {
        is QuizUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is QuizUiState.Success -> {
            Log.d("count" , "$count")
            QuizQuestion(
                data = quizUiState.info[count],
                count = count,
                viewModel = viewModel,
                onPreviousClicked = {viewModel.previousQuestion()},
                onNextClicked = {viewModel.nextQuestion()},
                onTimerFinished = onTimerFinished

            )
        }
        is QuizUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }



}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = null
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
    }
}



@Composable
fun QuizQuestion(
    data: ShuffledOptions,
    count: Int,
    viewModel: QuizViewModel,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onTimerFinished: () -> Unit

    ) {

    var selectedOption by remember { mutableStateOf(-1) }

    LaunchedEffect(viewModel.count.coerceIn(0,viewModel.result.size-1)) {

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFE8C4))) {
        QuestionCard(
            question = data.question,
            option1 = data.option1,
            option2 = data.option2,
            option3 = data.option3,
            option4 = data.option4,
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(16.dp)),
            viewModel = viewModel,
            questionIndex = count,
            selectedOption = selectedOption,
            onOptionSelected = {option ->
                selectedOption = option

            },
            totalTime = viewModel.timerValue,
            onTimerFinished = onTimerFinished


        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.prev),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .clickable { onPreviousClicked() },
                contentScale = ContentScale.FillBounds
                )

            if(viewModel.count==9) {
                Image(
                    painter = painterResource(R.drawable.submit),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                        .height(50.dp)
                        .clickable { viewModel.submissionPromptOpen = true },
                    contentScale = ContentScale.FillBounds

                )
            } else {
                Image(
                    painter = painterResource(R.drawable.next),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                        .height(50.dp)
                        .clickable { onNextClicked() },
                    contentScale = ContentScale.FillBounds

                )
            }
        }

    }

}

@Composable
fun QuestionCard(
    question: String,
    option1: String,
    option2: String,
    option3: String,
    option4: String,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
    questionIndex: Int,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
    totalTime: Long,
    onTimerFinished: () -> Unit


) {
    val selectedOptionsIndex by rememberUpdatedState(viewModel.getSelectedOption(questionIndex))
    var Clicked1 by remember { mutableStateOf(viewModel.isClicked1)}
    var Clicked2 by remember { mutableStateOf(viewModel.isClicked2)}
    var Clicked3 by remember { mutableStateOf(viewModel.isClicked3)}
    var Clicked4 by remember { mutableStateOf(viewModel.isClicked4)}

    Box(modifier = modifier) {
        Column(modifier = Modifier
            .padding(50.dp)
            .background(Color(0xFFF99E4D))
            .align(Alignment.Center)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Image(
                        painter = painterResource(R.drawable.ant_design_bulb_outlined),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(20.dp)
                            .clickable { }
                    )

                    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {

                        Timer(totalTime = totalTime, onTimerFinished = onTimerFinished )
                        Spacer(modifier = modifier.width(4.dp))
                        Image(
                            painter = painterResource(R.drawable.icon_park_outline_timer),
                            contentDescription = null,
                            modifier = Modifier

                                .padding(10.dp)
                                .size(20.dp)
                                .clickable { }
                        )
                    }
                }
            }



            Column(modifier = Modifier
                .padding(10.dp)) {
                Text(
                    text = question,
                    modifier = Modifier.padding(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { Clicked1=!Clicked1; Clicked2=false;Clicked3=false;Clicked4=false;
                        if(Clicked1) {
                            viewModel.setSelectedOptions(questionIndex,1);onOptionSelected(1)
                        } else {viewModel.setSelectedOptions(questionIndex,null);
                            onOptionSelected(-1)}
                    },
                    colors = ButtonDefaults.buttonColors(
                        if(selectedOptionsIndex==1) {
                            Color(0xFF8BC34A)
                        } else {
                            Color(0xFFFFC107)
                        }
                    ),
                    modifier = modifier
                ) {
                    Text(
                        text = option1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Black

                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { Clicked2=!Clicked2; Clicked1=false;Clicked3=false;Clicked4=false;
                        if(Clicked2) {
                            viewModel.setSelectedOptions(questionIndex,2);onOptionSelected(2)
                        } else {viewModel.setSelectedOptions(questionIndex,null);
                            onOptionSelected(-1)}
                    },
                    colors = ButtonDefaults.buttonColors(
                        if(selectedOptionsIndex==2) {
                            Color(0xFF8BC34A)
                        } else {
                            Color(0xFFFFC107)
                        }
                    )
                ) {
                    Text(
                        text = option2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Black

                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { Clicked3=!Clicked3; Clicked1=false;Clicked2=false;Clicked4=false;
                        if(Clicked3) {
                            viewModel.setSelectedOptions(questionIndex,3);onOptionSelected(3)
                        } else {viewModel.setSelectedOptions(questionIndex,null);
                            onOptionSelected(-1)}
                              },
                    colors = ButtonDefaults.buttonColors(
                        if(selectedOptionsIndex==3) {
                            Color(0xFF8BC34A)
                        } else {
                            Color(0xFFFFC107)
                        }
                    )
                ) {
                    Text(
                        text = option3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Black

                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { Clicked4=!Clicked4; Clicked1=false;Clicked2=false;Clicked3=false;
                        if(Clicked4) {
                            viewModel.setSelectedOptions(questionIndex,4);onOptionSelected(4)
                        } else {viewModel.setSelectedOptions(questionIndex,null);
                            onOptionSelected(-1)}
                    },
                    colors = ButtonDefaults.buttonColors(
                        if(selectedOptionsIndex==4) {
                            Color(0xFF8BC34A)
                        } else {
                            Color(0xFFFFC107)
                        }
                    )
                ) {
                    Text(
                        text = option4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Black

                    )
                }
            }
        }
    }
}

@Composable
fun Timer(
    totalTime: Long,
    modifier: Modifier = Modifier,
    initialValue: Float = 0f,
    onTimerFinished: () -> Unit
) {
    //var value by remember { mutableStateOf(initialValue)}
    var currentTime by remember { mutableStateOf(totalTime) }
    val coroutineScope = rememberCoroutineScope()

    /*LaunchedEffect(key1 = currentTime ) {
        if(currentTime>0) {
            delay(10L)
            currentTime -= 100L
        } else {
            onTimerFinished()
        }
    }*/
    DisposableEffect(Unit) {
        val timerJob = coroutineScope.launch {
            while(currentTime > 0) {
                delay(100L)
                currentTime -= 100L
            }

            onTimerFinished()

        }

        onDispose {
            timerJob.cancel()
        }

    }

    Box() {
        Text(
            text = (currentTime / 1000L).toString(),
            fontSize = 20.sp,
            modifier = modifier.padding(8.dp)
        )
    }
}


@Preview
@Composable
fun QuestionPagePreview() {
    QuizTheme(useDarkTheme = false) {
        //QuizQuestion(question = "How many times did Martina Navratilova win the Wimbledon Singles Championship?", option1 = "Nine", option2 = "Ten", option3 = "Seven", option4 = "Eight")
        
    }
}