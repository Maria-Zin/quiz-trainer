package ru.fefu.quiztrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fefu.quiztrainer.ui.QuestionScreen
import ru.fefu.quiztrainer.ui.ResultScreen
import ru.fefu.quiztrainer.ui.WelcomeScreen
import ru.fefu.quiztrainer.ui.theme.QuizTrainerTheme
import ru.fefu.quiztrainer.viewmodels.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTrainerTheme {
                val viewModel: QuizViewModel = viewModel()
                val state by viewModel.uiState.collectAsState()

                if (state.questions.isEmpty()) {
                    WelcomeScreen(
                        onStartClicked = { viewModel.startNewQuiz() }
                    )
                } else if (state.isQuizFinished) {
                    ResultScreen(
                        score = state.score,
                        totalQuestions = state.questions.size,
                        onRestartClicked = { viewModel.startNewQuiz() }
                    )
                } else {
                    QuestionScreen(
                        question = state.questions[state.currentQuestionIndex],
                        questionNumber = state.currentQuestionIndex + 1,
                        totalQuestions = state.questions.size,
                        selectedAnswerIndex = state.selectedAnswerIndex,
                        onAnswerSelected = { viewModel.selectAnswer(it) },
                        onSubmit = { viewModel.submitAnswer() }
                    )
                }
            }
        }
    }
}