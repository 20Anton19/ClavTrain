package com.example.clavtrain.ui.user

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.ui.theme.ClavTrainTheme

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import com.example.clavtrain.data.db.DataBaseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    barColor: Color = Color.Blue,
    maxValue: Float? = null
) {
    val maxBarValue = maxValue ?: data.maxOfOrNull { it.value } ?: 1f

    Canvas(modifier = modifier.fillMaxWidth().height(280.dp).padding(0.dp,30.dp,45.dp)) {
        // ИЗМЕНЕНИЕ 1: Увеличиваем левый отступ для подписи оси Y
        val leftPadding = 100f // Было 60f - увеличили для подписи
        val chartHeight = size.height - 80f
        val chartTop = 50f

        val barWidth = size.width / data.size * 0.7f
        val spacing = size.width / data.size * 0.3f

        // Ось Y
        drawLine(
            color = Color.Black,
            start = Offset(leftPadding, chartTop),
            end = Offset(leftPadding, chartHeight),
            strokeWidth = 2f
        )

        // Ось X
        drawLine(
            color = Color.Black,
            start = Offset(leftPadding, chartHeight),
            end = Offset(size.width, chartHeight),
            strokeWidth = 2f
        )

        // Подписи значений на оси Y
        val ySteps = 5
        for (i in 0..ySteps) {
            val value = (maxBarValue * i / ySteps).toInt()
            val y = chartHeight - (chartHeight - chartTop) * i / ySteps

            drawContext.canvas.nativeCanvas.drawText(
                value.toString(),
                leftPadding - 15f, // Сдвигаем от оси
                y + 10f,
                Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 30f
                    textAlign = Paint.Align.RIGHT
                }
            )

            // Линии сетки
            drawLine(
                color = Color.Gray.copy(alpha = 0.3f),
                start = Offset(leftPadding, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }

        data.forEachIndexed { index, barData ->
            val barHeight = (barData.value / maxBarValue) * (chartHeight - chartTop)
            val left = leftPadding + 10f + index * (barWidth + spacing) // Учитываем новый отступ
            val top = chartHeight - barHeight

            drawRoundRect(
                color = barColor,
                topLeft = Offset(left, top),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )

            // Подпись значения над столбцом
            drawContext.canvas.nativeCanvas.drawText(
                barData.value.toInt().toString(),
                left + barWidth / 2,
                top - 15f,
                Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 32f
                    textAlign = Paint.Align.CENTER
                    isFakeBoldText = true
                }
            )

            // Подпись категории под осью X
            drawContext.canvas.nativeCanvas.drawText(
                barData.label,
                left + barWidth / 2,
                chartHeight + 40f,
                Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 36f
                    textAlign = Paint.Align.CENTER
                }
            )
        }

        // ИЗМЕНЕНИЕ 2: Подпись оси Y - сдвигаем дальше от графика
        drawContext.canvas.nativeCanvas.save()
        drawContext.canvas.nativeCanvas.rotate(-90f, 40f, size.height / 2) // Сдвигаем влево
        drawContext.canvas.nativeCanvas.drawText(
            "Количество ошибок",
            50f, // Было 25f - сдвигаем дальше от оси
            size.height / 2,
            Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 34f
                textAlign = Paint.Align.CENTER
            }
        )
        drawContext.canvas.nativeCanvas.restore()

        // Подпись оси X
        drawContext.canvas.nativeCanvas.drawText(
            "Попытка выполнения",
            size.width / 2 + 90f,
            size.height + 10f,
            Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 34f
                textAlign = Paint.Align.CENTER
            }
        )
    }
}

data class BarData(
    val value: Float,
    val label: String
)
@Composable
fun UserExerciseStatisticScreen(
    onViewMenu: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
    val dataList = listOf<BarData>(
        BarData(10f, "авава"),
        BarData(10f, "авава"),
        BarData(10f, "авава"),
        BarData(10f, "авава"),
        BarData(10f, "авава")
    )

    val statistic by dataBaseViewModel.lastStatistic.collectAsState()
    // Показываем загрузку если статистика еще не пришла
    if (statistic == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text("Загрузка статистики...", modifier = Modifier.padding(16.dp))
        }
    } else {
        val statisticListInfo = listOf(
            "Время ${"%.2f".format(statistic!!.timeSpent.toDouble() / 1000)} сек",
            "Ошибки ${statistic!!.mistakes}",
            "Среднее время нажатия ${"%.2f".format(statistic!!.avgTime.toDouble())} мс"
            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Статистика по упражнению",
                //style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                //fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            LazyColumn {
                items(statisticListInfo) { item ->
                    Card(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .width(250.dp)
                            .height(50.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xffe6d9e8))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
            BarChart(
                data = dataList,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Button(
                onClick = onViewMenu,
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Выйти")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun UserExerciseStatisticScreenPreview() {
    ClavTrainTheme { UserExerciseStatisticScreen(onViewMenu = {}) }
}