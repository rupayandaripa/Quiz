package com.example.quiz.Data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizData(
    @SerialName("response_code")
    val responseCode: Int,
    val results: List<Question>
)

@Serializable
data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>
)

data class ShuffledOptions(
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctOptionIndex: Int
)