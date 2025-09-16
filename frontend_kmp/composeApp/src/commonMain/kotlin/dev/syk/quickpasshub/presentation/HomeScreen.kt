package dev.syk.quickpasshub.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(){
    Column(){
        Text("Quick Pass Hub")
        Spacer(Modifier.height(8.dp))

        val exams = remember {
            listOf(
                ExamUi("1", "정보처리기사 필기", ""),
                ExamUi("2", "정보처리기사 실기", "")
            )
        }

        exams.forEach { exam ->
            ElevatedCard {
                Column {
                    Text(exam.title)
                }
            }
        }

    }
}

private data class ExamUi(val id: String, val title: String, val type: String)