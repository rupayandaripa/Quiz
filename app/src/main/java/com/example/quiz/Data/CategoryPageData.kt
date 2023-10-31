package com.example.quiz.Data

import com.example.quiz.R

object CategoryPageData {
    val categories: List<CategoryPageType> = listOf(
        CategoryPageType(
            pictures = R.drawable.group_19,
            type = CategoryPageUtils.SPORTS,
            category = CategoryPageUtils.SPORTS.title
        ),

        CategoryPageType(
            pictures = R.drawable.group_20,
            type = CategoryPageUtils.NATURE,
            category = CategoryPageUtils.NATURE.title
        ),

        CategoryPageType(
            pictures = R.drawable.group_21,
            type = CategoryPageUtils.HISTORY,
            category = CategoryPageUtils.HISTORY.title
        ),

        CategoryPageType(
            pictures = R.drawable.new_animal,
            type = CategoryPageUtils.ANIMALS,
            category = CategoryPageUtils.ANIMALS.title
        ),

        CategoryPageType(
            pictures = R.drawable.group_38,
            type = CategoryPageUtils.SCIENCE,
            category = CategoryPageUtils.SCIENCE.title
        ),

        CategoryPageType(
            pictures = R.drawable.group_37,
            type = CategoryPageUtils.MOVIE,
            category = CategoryPageUtils.MOVIE.title
        )
    )
}

