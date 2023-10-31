package com.example.quiz.ui.theme

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.quiz.R
import com.example.quiz.model.QuizViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import com.example.quiz.Data.CategoryPageData
import com.example.quiz.Data.CategoryPageType
import com.example.quiz.Data.CategoryPageUtils


@Composable
fun QuizApp(
    viewModel: QuizViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    context: Context
) {


    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CategoryPageUtils.valueOf(
        backStackEntry?.destination?.route?:CategoryPageUtils.HOMEPAGE.name
    )

    val rememberedCount by remember{ mutableStateOf(viewModel.count)}
    val selectedCategory by remember { mutableStateOf<CategoryPageType?>(null)}
    var isBurgerMenuOpen by remember { mutableStateOf(false) }

    BackHandler(
        onBack = {
            if (currentScreen == CategoryPageUtils.SCORE_PAGE) {
                // Navigate back to the landing page from the score page
                navController.navigate(CategoryPageUtils.LANDING_PAGE.name) {
                    popUpTo(CategoryPageUtils.LANDING_PAGE.name) {
                        inclusive = true
                    }
                }
            } else {
                // Navigate back to the previous screen if possible
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            }
        }
    )



    Scaffold(
        topBar = {
            QuizAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                goToFirstPage = {navController.navigate(CategoryPageUtils.LANDING_PAGE.name)},
                goToHomePage = {navController.navigate(CategoryPageUtils.HOMEPAGE.name)},
                openBurgerMenu = { isBurgerMenuOpen = !isBurgerMenuOpen },
                navController = navController
            )

        }
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CategoryPageUtils.HOMEPAGE.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = CategoryPageUtils.HOMEPAGE.name) {
                HomeScreen(
                    onClick = {
                        navController.navigate(viewModel.homePageButton().name)
                    }
                )
            }
            composable(route = CategoryPageUtils.LANDING_PAGE.name) {
                if(isBurgerMenuOpen) {
                    LandingPageWithBurgerMenu(navController = navController , onClick = {isBurgerMenuOpen = false})
                } else {
                    LandingPageContent(
                        modifier = Modifier.background(Color(0xFFFFE8C4)),
                        navController = navController,
                        onClick = {
                            navController.navigate(viewModel.landingPageButton().name)
                        }
                    )
                }
            }



            composable(route = CategoryPageUtils.CATEGORY_PAGE.name) {
                if(isBurgerMenuOpen) {
                    CategoryPageWithBurgerMenu(navController = navController , onClick = {isBurgerMenuOpen = false} , categoryList = CategoryPageData.categories , context = context)
                } else {
                    CategoryPage(
                        navController = navController,
                        categoryList = CategoryPageData.categories,
                        onClick = {selectedCategory ->

                            viewModel.selectedCategory(selectedCategory)
                            //viewModel.getQuizQuestions(context,selectedCategory.type, refreshInterval = Long.MAX_VALUE)
                            navController.navigate(viewModel.determineQuestionsType().name)

                        },
                        context = context
                    )

                }
            }

            composable(route = CategoryPageUtils.PLAY_NOW_PAGE.name) {
                viewModel.selectedCategory?.let { it1 ->
                    PlayNowScreen(
                        category = it1,
                        playNow = {
                            viewModel.getQuizQuestions(context , it1 , refreshInterval = Long.MAX_VALUE)
                            navController.navigate(viewModel.playNowButton(it1).name)
                            viewModel.submissionPromptOpen = false

                        }
                    )
                }
                }
            composable(route = CategoryPageUtils.SPORTS.name) {
                //viewModel.selectedCategory(CategoryPageUtils.SPORTS)
                val questions = viewModel.quizAppUiState
                if(viewModel.submissionPromptOpen) {
                    QuizScreenWithPrompt(quizUiState = questions, viewModel = viewModel , goToScorePage = { navController.navigate(viewModel.goToScorePage().name)} , onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })})
                } else {
                    QuizScreen(
                        quizUiState = questions,
                        viewModel = viewModel,
                        onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })}
                    )
                }
            }

            composable(route = CategoryPageUtils.MOVIE.name) {
                //viewModel.selectedCategory(CategoryPageUtils.MOVIE)
                val questions = viewModel.quizAppUiState
                if(viewModel.submissionPromptOpen) {
                    QuizScreenWithPrompt(quizUiState = questions, viewModel = viewModel, goToScorePage = { navController.navigate(viewModel.goToScorePage().name)} , onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })})
                } else {
                    QuizScreen(
                        quizUiState = questions,
                        viewModel = viewModel,
                        onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })}
                    )
                }
            }

            composable(route = CategoryPageUtils.SCIENCE.name) {
                val questions = viewModel.quizAppUiState
                if(viewModel.submissionPromptOpen) {
                    QuizScreenWithPrompt(quizUiState = questions, viewModel = viewModel, goToScorePage = { navController.navigate(viewModel.goToScorePage().name)} , onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })})
                } else {
                    QuizScreen(
                        quizUiState = questions,
                        viewModel = viewModel,
                        onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })}
                    )
                }
            }

            composable(route = CategoryPageUtils.ANIMALS.name) {

                val questions = viewModel.quizAppUiState
                if(viewModel.submissionPromptOpen) {
                    QuizScreenWithPrompt(quizUiState = questions, viewModel = viewModel, goToScorePage = { navController.navigate(viewModel.goToScorePage().name)},onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })})
                } else {
                    QuizScreen(
                        quizUiState = questions,
                        viewModel = viewModel,
                        onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })}
                    )
                }
            }

            composable(route = CategoryPageUtils.HISTORY.name) {

                val questions = viewModel.quizAppUiState
                if(viewModel.submissionPromptOpen) {
                    QuizScreenWithPrompt(quizUiState = questions, viewModel = viewModel , goToScorePage = { navController.navigate(viewModel.goToScorePage().name)},onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })})
                } else {
                    QuizScreen(
                        quizUiState = questions,
                        viewModel = viewModel,
                        onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })}
                    )
                }
            }


            composable(route = CategoryPageUtils.NATURE.name) {

                val questions = viewModel.quizAppUiState
                if(viewModel.submissionPromptOpen) {
                    QuizScreenWithPrompt(quizUiState = questions, viewModel = viewModel , goToScorePage = { navController.navigate(viewModel.goToScorePage().name) },onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })})
                } else {
                    QuizScreen(
                        quizUiState = questions,
                        viewModel = viewModel,
                        onTimerFinished = {navController.navigate(CategoryPageUtils.SCORE_PAGE.name, builder = {popUpTo(CategoryPageUtils.LANDING_PAGE.name){inclusive = true} })}
                    )
                }
            }
            
            composable(
                route = CategoryPageUtils.SCORE_PAGE.name,


            ) {
                val score = viewModel.getScore()
                ScorePage(progress = (score.toFloat())/10)
            }


            }
        }

    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizAppBar(
    currentScreen: CategoryPageUtils,
    canNavigateBack: Boolean,
    goToFirstPage: () -> Unit = {},
    openBurgerMenu: () -> Unit = {},
    navigateUp: ()-> Unit = {},
    goToHomePage: ()->Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController

) {
    if(currentScreen != CategoryPageUtils.HOMEPAGE) {
        TopAppBar(
            title = {Text(stringResource(R.string.topbar))},
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFFFDC450),

                ),
            modifier = modifier,
            navigationIcon = {
                if(currentScreen != CategoryPageUtils.SPORTS && currentScreen != CategoryPageUtils.NATURE && currentScreen != CategoryPageUtils.HISTORY && currentScreen != CategoryPageUtils.ANIMALS && currentScreen != CategoryPageUtils.SCIENCE && currentScreen != CategoryPageUtils.MOVIE) {
                    IconButton(onClick =  {
                        if(currentScreen==CategoryPageUtils.LANDING_PAGE) {
                            goToHomePage()
                        }
                        else if(currentScreen == CategoryPageUtils.SCORE_PAGE) {
                            navController.navigate(CategoryPageUtils.LANDING_PAGE.name) {
                                popUpTo(CategoryPageUtils.LANDING_PAGE.name) {
                                    inclusive = true
                                }
                            }
                        }

                        else {
                            navigateUp()
                        }


                    } ) {
                        Image(
                            painter = painterResource(R.drawable.ic_round_arrow_back_ios),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                } else {
                    IconButton(onClick =  { goToFirstPage()}

                    ) {
                        Image(
                            painter = painterResource(R.drawable.group_41),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                }


            },
            actions = {
                IconButton(onClick =  openBurgerMenu ) {
                    Image(
                        painter = painterResource(R.drawable.group_5),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }



        )
    }
}