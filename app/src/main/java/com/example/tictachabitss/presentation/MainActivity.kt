package com.example.tictachabitss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import com.example.tictachabitss.ui.HabitListScreen
import viewmodel.HabitViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HabitViewModel = viewModel()
            MaterialTheme {
                HabitListScreen(viewModel)
            }
        }
    }
}
