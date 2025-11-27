package martinvergara_diegoboggle.pawschedule.ui
//LAS ANIMACIONES PARA EL FEEDBACK INMEDIATO
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun BounceButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    val interactionSource = remember { MutableInteractionSource() } //ESTA ES LA OPCION QUE ACTIVA PARA ESCUCHAR EL BOTON

    val isPressed by interactionSource.collectIsPressedAsState() //ESTO RESCATA SI ESTA O NO PRESIONADO


    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {

                scaleX = scale
                scaleY = scale
            },
        interactionSource = interactionSource, // CONECTA EL ESCUCHAR
        colors = colors,
        contentPadding = contentPadding
    ) {
        Text(text = text)
    }
}