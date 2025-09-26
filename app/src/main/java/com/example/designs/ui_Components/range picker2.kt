package com.example.designs.ui_Components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun RangePicker2() {

    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color.Black, Color(0xFF0A1A0A)),
                    startX = 0f,
                    endX = Float.POSITIVE_INFINITY
                )
            )
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val boxSize = 300f // Height of the curve area
            val depth = 60f      // How much the curve extends outwards
            val lineX = size.width * 0.25f // X position of the vertical line
            val curveCenterY = center.y

            // 1. --- The main timeline path ---
            val timelinePath = Path().apply {
                // Top vertical line
                moveTo(lineX, 0f)
                lineTo(lineX, curveCenterY - (boxSize / 2))

                // Top part of the S-curve
                cubicTo(
                    x1 = lineX,
                    y1 = curveCenterY - (boxSize / 2) + 70,
                    x2 = lineX + depth,
                    y2 = curveCenterY - 70,
                    x3 = lineX + depth,
                    y3 = curveCenterY
                )

                // Bottom part of the S-curve
                cubicTo(
                    x1 = lineX + depth,
                    y1 = curveCenterY + 70,
                    x2 = lineX,
                    y2 = curveCenterY + (boxSize / 2) - 70,
                    x3 = lineX,
                    y3 = curveCenterY + (boxSize / 2)
                )

                // Bottom vertical line
                lineTo(lineX, size.height)
            }

            drawPath(
                path = timelinePath,
                color = Color.Green.copy(alpha = 0.4f),
                style = Stroke(width = 3.dp.toPx())
            )


            // 2. --- The central circle button ---
            val circleCenterX = lineX + depth
            val circleRadius = 30.dp.toPx()

            drawCircle(
                color = Color.Black.copy(alpha = 0.5f),
                radius = circleRadius,
                center = Offset(x = circleCenterX, y = curveCenterY)
            )
            drawCircle(
                color = Color.Gray.copy(alpha = 0.5f),
                radius = circleRadius,
                center = Offset(x = circleCenterX, y = curveCenterY),
                style = Stroke(width = 1.dp.toPx())
            )

            // Arrow inside the circle
            val arrowPath = Path().apply {
                val arrowSize = 5.dp.toPx()
                moveTo(circleCenterX, curveCenterY - arrowSize) // Top point
                lineTo(circleCenterX - arrowSize, curveCenterY + arrowSize / 2) // Bottom-left
                lineTo(circleCenterX + arrowSize, curveCenterY + arrowSize / 2) // Bottom-right
                close()
            }
            drawPath(arrowPath, color = Color.White, style = Stroke(width = 1.5.dp.toPx()))


            // 3. --- The date labels on the left ---
            val dates = listOf("Sept 2024", "Oct 2024", "Nov 2024", "Dec 2024", "Jan 2025", "Feb 2025")
            val totalHeightForDates = size.height * 0.7f
            val startY = center.y - totalHeightForDates / 2
            val spacing = totalHeightForDates / (dates.size - 1)

            dates.forEachIndexed { index, date ->
                val yPos = startY + index * spacing
                val isNearCurve = yPos > curveCenterY - boxSize * 0.6f && yPos < curveCenterY + boxSize * 0.6f

                drawText(
                    textMeasurer = textMeasurer,
                    text = date,
                    style = TextStyle(
                        color = if (isNearCurve) Color.White else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (isNearCurve) FontWeight.Bold else FontWeight.Normal,
                        textAlign = androidx.compose.ui.text.style.TextAlign.End
                    ),
                    topLeft = Offset(lineX - 120.dp.toPx(), yPos - (12.sp.toPx() / 2)),
                    size = androidx.compose.ui.geometry.Size(100.dp.toPx(), 20.dp.toPx())
                )

                if (!isNearCurve) {
                    // Draw tick mark
                    drawLine(
                        color = Color.Green.copy(alpha = 0.4f),
                        start = Offset(lineX, yPos),
                        end = Offset(lineX - 10.dp.toPx(), yPos),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }

            // 4. --- The main content on the right ---
            val rightContentX = size.width * 0.55f

            drawText(
                textMeasurer = textMeasurer,
                text = "Praha",
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                topLeft = Offset(rightContentX, curveCenterY - 70.dp.toPx())
            )

            drawText(
                textMeasurer = textMeasurer,
                text = "8,830",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                ),
                topLeft = Offset(rightContentX, curveCenterY - 40.dp.toPx())
            )

            drawText(
                textMeasurer = textMeasurer,
                text = "Followers",
                style = TextStyle(color = Color.Gray, fontSize = 16.sp),
                topLeft = Offset(rightContentX, curveCenterY + 35.dp.toPx())
            )
        }
    }
}
