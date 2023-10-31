package com.example.quiz.Data

import androidx.annotation.StringRes
import com.example.quiz.R

class CategoryPageType (
    val pictures: Int,
    val type: CategoryPageUtils,
    @StringRes val category: Int,

    )

enum class CategoryPageUtils(@StringRes val title: Int, @StringRes val Id: Int) {
    SPORTS(R.string.sports , 21),
    NATURE(R.string.nature , 17),
    HISTORY(R.string.history , 23),
    ANIMALS(R.string.animals , 27),
    SCIENCE(R.string.science , 18),
    MOVIE(R.string.movie , 11),
    HOMEPAGE(R.string.homepage,0),
    LANDING_PAGE(R.string.landingpage,1),
    CATEGORY_PAGE(R.string.categoryPage,2),
    CONFIRM_PAGE(R.string.confirmPage,3),
    QUESTIONS_PAGE(R.string.questionsPage,4),
    SCORE_PAGE(R.string.scorePage,5),
    PLAY_NOW_PAGE(R.string.play_now , 6)

}

