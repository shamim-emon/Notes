package bd.emon.notes.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bd.emon.notes.R

val NunitoFontFamily = FontFamily(
    listOf(
        Font(R.font.nunito_regular),
        Font(R.font.nunito_light, FontWeight.Light),
        Font(R.font.nunito_semibold, FontWeight.SemiBold)
    )
)

val Typography = Typography(

    headlineSmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp,
        fontSize = 25.sp
    ),

    titleLarge = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.sp
    ),

    displayMedium = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 43.sp,
        lineHeight = 47.sp,
        letterSpacing = 0.sp
    ),

    displaySmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)