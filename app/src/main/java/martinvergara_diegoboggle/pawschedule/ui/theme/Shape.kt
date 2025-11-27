package martinvergara_diegoboggle.pawschedule.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),  // Para TextFields y Chips
    medium = RoundedCornerShape(16.dp), // Para Cards y Dialogs
    large = RoundedCornerShape(24.dp),  // Para Botones grandes
    extraLarge = RoundedCornerShape(32.dp) // Para BottomSheets
)