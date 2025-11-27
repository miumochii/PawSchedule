package martinvergara_diegoboggle.pawschedule.ui

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
    // 1. Creamos una fuente de interacción para "escuchar" al botón
    val interactionSource = remember { MutableInteractionSource() }

    // 2. Detectamos si el botón está presionado o no
    val isPressed by interactionSource.collectIsPressedAsState()

    // 3. Animación: Si está presionado baja a 0.90f, si no, vuelve a 1f
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f, // Ajusta 0.90f para más o menos rebote
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, // Rebote elástico
            stiffness = Spring.StiffnessLow // Velocidad suave
        ),
        label = "scaleAnimation"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                // Aplicamos la escala aquí
                scaleX = scale
                scaleY = scale
            },
        interactionSource = interactionSource, // Conectamos el "escucha"
        colors = colors,
        contentPadding = contentPadding
    ) {
        Text(text = text)
    }
}