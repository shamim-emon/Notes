package bd.emon.notes.ui.theme

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

// Set of Material typography styles to start with
val Typography = Typography(

    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.sp
    ),

    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 43.sp,
        lineHeight = 47.sp,
        letterSpacing = 0.sp
    ),

    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
//        lineHeight = 27.sp,
//        letterSpacing = 10.sp
    ),



    /* Other default text styles to override
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)