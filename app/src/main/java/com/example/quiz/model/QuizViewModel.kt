package com.example.quiz.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.Data.CategoryPageType
import com.example.quiz.Data.CategoryPageUtils
import com.example.quiz.Data.Question
import com.example.quiz.Data.QuizData
import com.example.quiz.Data.ShuffledOptions
import com.example.quiz.network.QuizApi
import com.example.quiz.network.QuizRepository
import kotlinx.coroutines.launch

sealed interface QuizUiState {
    data class Success(val info: /*QuizData*/List<ShuffledOptions>) : QuizUiState
    object Error : QuizUiState
    object Loading : QuizUiState
}

class QuizViewModel: ViewModel() {

    var isClicked1: Boolean by mutableStateOf(false)
    var isClicked2: Boolean by mutableStateOf(false)
    var isClicked3: Boolean by mutableStateOf(false)
    var isClicked4: Boolean by mutableStateOf(false)
    var timerValue: Long by mutableStateOf(100L * 1000L)


    var quizAppUiState: QuizUiState by mutableStateOf(QuizUiState.Loading)
        private set

    private val fetchedQuestionsMap = mutableMapOf<Int, QuizData>()


    fun homePageButton(): CategoryPageUtils {
        return CategoryPageUtils.LANDING_PAGE
    }

    fun landingPageButton(): CategoryPageUtils {
        return CategoryPageUtils.CATEGORY_PAGE
    }

    fun determineQuestionsType(): CategoryPageUtils {
        return CategoryPageUtils.PLAY_NOW_PAGE
    }

    fun playNowButton(type: CategoryPageType): CategoryPageUtils {
        return type.type
    }

    fun goToScorePage(): CategoryPageUtils {
        return CategoryPageUtils.SCORE_PAGE
    }


    var selectedCategory: CategoryPageType? = null
    private var lastSelectedCategory: CategoryPageUtils? = null
    fun selectedCategory(category: CategoryPageType) {
        selectedCategory = category
    }

    fun shuffleOptions(questions: Question): ShuffledOptions {
        var options = mutableListOf(questions.correctAnswer) + questions.incorrectAnswers
        options = options.shuffled()
        val correctOptionIndex = options.indexOf(questions.correctAnswer)
        return ShuffledOptions(
            question = questions.question,
            option1 = options[0],
            option2 = options[1],
            option3 = options[2],
            option4 = options[3],
            correctOptionIndex = correctOptionIndex
        )
    }

    var result: List<ShuffledOptions> = mutableListOf()
    var listResult: QuizData? by  mutableStateOf(null)


    fun getQuizQuestions(context: Context, category: CategoryPageType, refreshInterval: Long) {

        selectedCategory = category

        selectedOptionsMap.clear()

        viewModelScope.launch {
            try {
                listResult = QuizApi.retrofitService.getQuestions(category = category.type.Id)

                val shuffledOptionsList = mutableListOf<ShuffledOptions>()

                var index = 0

                while (index < listResult!!.results.size) {
                    val shuffledOptions = shuffleOptions(listResult!!.results[index])
                    shuffledOptionsList.add(shuffledOptions)
                    index++
                }

                result = shuffledOptionsList

                Log.d("questionList" , "$result")


                quizAppUiState = QuizUiState.Success(result)
                fetchedQuestionsMap[category.type.Id] = listResult!! // Store fetched questions
                QuizRepository.saveFetchTimestamp(context) // Save fetch timestamp
            } catch (e: Exception) {
                quizAppUiState = QuizUiState.Error
            }
        }


    }

    var count: Int by mutableStateOf(0)
        private set

    private val selectedOptionsMap = mutableMapOf<Int, Int?>()

    fun getSelectedOption(questionsIndex: Int): Int? {
        return selectedOptionsMap[questionsIndex]
    }

    fun setSelectedOptions(questionsIndex: Int, optionIndex: Int?) {
        selectedOptionsMap[questionsIndex] = optionIndex
    }

    init {
        for (i in 0 until 10) {
            selectedOptionsMap[i] = -1
        }
    }
    var Quizscore: Int by mutableStateOf(0)

    fun nextQuestion() {

        if (count < result.size - 1) {
            count++

        }
    }



    fun previousQuestion() {
        if (count > 0) {
            count--
        }
    }

    var submissionPromptOpen: Boolean by mutableStateOf(false)

    fun getScore(): Int {
        var score = 0

        for(index in 0 until result.size) {
            val selectedOptionsIndex = selectedOptionsMap[index]?.minus(1)
            val correctOptionsIndex = result[index].correctOptionIndex

            if(selectedOptionsIndex == correctOptionsIndex) {
                score++
            }


        }
        return score

    }


}





