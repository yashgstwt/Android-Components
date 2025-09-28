package com.example.designs.ui_Components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.DropShadowPainter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designs.ui_Components.Constants.dates
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.sin

@Preview(showSystemUi = true)
@Composable
fun RangePicker2() {

    val boxSize = 500f // Total height of the S-curve section of the notch path
    val depth = -80f   // Depth of the notch (negative means to the left of center.x)
    val color = Color(0xFF74AB2D)
    val lineColor = listOf<Color>(Color.Transparent,Color(0xFF727770) , Color.Transparent)
    val buttonColor = Color(0xFF192028)
    val textMeasurer = rememberTextMeasurer()
    val textMeasurer2 = rememberTextMeasurer()
    var currentSelectedOption by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)){

        // First Canvas (static elements - the notch path)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(center.x , 100f)
                lineTo(center.x, center.y - (boxSize/2) )
                cubicTo(x1= center.x  , y1 = center.y - (boxSize/2) + 90, x2 = center.x + depth , y2 = center.y - 90,x3 = center.x+depth , y3 = center.y )
                cubicTo(x1= center.x + depth , y1 = center.y + 90, x2 = center.x , y2 = center.y + (boxSize/2) - 90,x3 = center.x , y3 = center.y + (boxSize/2))
                lineTo(center.x, center.y + (boxSize/2) ) // Path segment D ends here, this lineTo is okay for clarity or if there were minor adjustments
                lineTo(center.x, size.height)
            }

            val shadowPath = Path().apply {

                moveTo(center.x, center.y - (boxSize/2) )
                cubicTo(x1= center.x  , y1 = center.y - (boxSize/2) + 90, x2 = center.x + depth , y2 = center.y - 90,x3 = center.x+depth , y3 = center.y )
                cubicTo(x1= center.x + depth , y1 = center.y + 90, x2 = center.x , y2 = center.y + (boxSize/2) - 90,x3 = center.x , y3 = center.y + (boxSize/2))
                lineTo(center.x, center.y + boxSize ) // Path segment D ends here, this lineTo is okay for clarity or if there were minor adjustments
                lineTo(center.x - boxSize ,center.y + boxSize )
                lineTo(center.x - boxSize ,center.y - boxSize )
                lineTo(center.x ,center.y - boxSize )
            }

            drawPath(
                path = shadowPath ,
                brush = Brush.radialGradient(listOf<Color>( color.copy(alpha = .4f), Color.Transparent ))
            )

            drawPath(
                path = path ,
                brush = Brush.linearGradient(listOf<Color>(Color.Transparent , color.copy(alpha = .5f) , Color.Transparent)),
                style = Stroke(width = 10f)
            )


            drawCircle( buttonColor , radius = 100f , center = Offset(x = center.x -(depth) + 60, center.y),)


            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        0.75f to Color.Transparent, // Shadow starts blending from 85% of the radius
                        1f to Color.White.copy(alpha = 0.05f) // It's darkest at the very edge
                    ),
                    center = Offset(x = center.x -(depth) + 60, center.y + 5),
                    radius = 100f
                ),
                radius = 100f  ,
                center = Offset(x = center.x -(depth) + 60, center.y),
            )
            drawText(
                textMeasurer = textMeasurer2,
                text = currentSelectedOption,
                style = TextStyle(
                    color = color,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End,
                ),
                topLeft = Offset(x = center.x -(depth) + 70, center.y  ),
                size = Size(100.dp.toPx(), 20.dp.toPx())
            )

        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val boxSize = 200f
            val containerHeight = constraints.maxHeight.toFloat()
            var currentScroll by remember { mutableFloatStateOf(0f) }

            val maxScroll = ((dates.size) * boxSize) + (containerHeight/12)

//            containerHeight : 2400.0
//            maxscroll : 3800.


            val scrollableState = remember {
                ScrollableState { delta ->
                    val newValue = (currentScroll + delta).coerceIn(0f, maxScroll)
                    val consumed = newValue - currentScroll
                    currentScroll = newValue
                    consumed
                }
            }


            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(
                        state = scrollableState,
                        orientation = Orientation.Vertical
                    )
            ) {
                val scrollOffset = currentScroll

                fun calculateDynamicPathX(yOnCanvas: Float, i: Int): Float {
                    val pathDefinitionCenterY = this.center.y
                    val curveStartY = pathDefinitionCenterY - boxSize / 2
                    val curveEndY = pathDefinitionCenterY + boxSize / 2

                    if (yOnCanvas < curveStartY || yOnCanvas > curveEndY) {
                        return this.center.x
                    } else {
                        if (yOnCanvas > curveStartY + 50 && yOnCanvas < curveEndY - 50) {
                            currentSelectedOption = dates[i]
                        }

                        val t = (yOnCanvas - curveStartY) / boxSize
                        val xOffset = depth * sin(t * PI.toFloat()).toFloat()
                        return this.center.x + xOffset
                    }
                }

                for (i in 1 until dates.size) {
                    val yPositionOnCanvas = (i * boxSize) - scrollOffset + containerHeight/2
                    val startX = calculateDynamicPathX(yPositionOnCanvas, i)
                    val endX = startX - 150f

                    drawLine(
                        brush = Brush.linearGradient(lineColor),
                        start = Offset(x = startX - 80, y = yPositionOnCanvas),
                        end = Offset(x = endX, y = yPositionOnCanvas),
                        strokeWidth = 7f,
                        cap = StrokeCap.Round
                    )

                    drawText(
                        textMeasurer = textMeasurer,
                        text = dates[i],
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End
                        ),
                        topLeft = Offset(startX - 480f, yPositionOnCanvas - 20f),
                        size = Size(100.dp.toPx(), 20.dp.toPx())
                    )
                }
            }
        }

    }
}
