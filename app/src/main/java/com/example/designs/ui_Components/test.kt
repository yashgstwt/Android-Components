import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A custom button Composable that replicates the provided image.
 * It features a circular design with a subtle gradient background and a glowing arrow icon.
 *
 * @param modifier The modifier to be applied to the button.
 * @param onClick The lambda to be executed when the button is clicked.
 */
@Composable
fun GlowingArrowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Define the colors based on the image provided
    val darkGradientStart = Color(0xFF3A4050)
    val darkGradientEnd = Color(0xFF1D2028)
    val arrowColor = Color(0xFFC8FF8A)
    val glowColor = Color(0xFFB9FF66)

    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape) // Clip the ripple effect to a circle
         ,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val center = this.center
            val radius = size.minDimension / 2f

            // 1. Draw the dark, circular background with a radial gradient for depth
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(darkGradientStart, darkGradientEnd),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // 2. Draw the inner shadow effect with another radial gradient
            drawCircle(
                brush = Brush.radialGradient(
                    center = center,
                    radius = radius,
                    colorStops = arrayOf(
                        0.85f to Color.Transparent, // Shadow starts blending from 85% of the radius
                        1f to Color.White.copy(alpha = 0.5f) // It's darkest at the very edge
                    )
                ),
                radius = radius,
                center = center
            )





//
        }
    }
}

/**
 * A preview Composable to display the GlowingArrowButton in Android Studio.
 * The background is set to a dark color to better visualize the button's appearance.
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PreviewGlowingArrowButton() {
    Box(modifier = Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
        GlowingArrowButton {}
    }
}

