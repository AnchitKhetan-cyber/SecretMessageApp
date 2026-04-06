package com.humblecoders.secretmessageapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults


@Composable
fun EncoderDecoderApp() {

    var inputEncode by remember { mutableStateOf("") }
    var inputDecode by remember { mutableStateOf("") }
    var encodedResult by remember { mutableStateOf("") }
    var decodedResult by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
    ) {

        Image(
            painterResource(R.drawable.screen_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.2f,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0F2027),
                            Color(0xFF203A43),
                            Color(0xFF2C5364)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(50.dp))

            Text(
                "Secret Message",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.5.sp
            )

            Text(
                "Encode & Decode your messages",
                fontSize = 14.sp,
                color = Color.LightGray.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(30.dp))

            GlassCard {
                CustomTextField(inputEncode, "Enter Text to Encode") {
                    inputEncode = it
                }

                Spacer(Modifier.height(16.dp))

                GradientButton(
                    "Encode",
                    listOf(Color.Cyan, Color.Blue)
                ) {
                    encodedResult =
                        if (inputEncode.isEmpty()) "" else encode(inputEncode)
                }

                Spacer(Modifier.height(16.dp))

                ResultBox(encodedResult)
            }

            Spacer(Modifier.height(48.dp))

            GlassCard {
                CustomTextField(inputDecode, "Enter Text to Decode") {
                    inputDecode = it
                }

                Spacer(Modifier.height(16.dp))

                GradientButton(
                    "Decode",
                    listOf(Color(0xFFFF4B2B), Color(0xFFFF416C))
                ) {
                    decodedResult =
                        if (inputDecode.isEmpty()) "" else decode(inputDecode)
                }

                Spacer(Modifier.height(16.dp))

                ResultBox(decodedResult)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun CustomTextField(value: String, label: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label, color = Color.White) },
        textStyle = TextStyle(color = Color.White),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.Cyan,
            focusedBorderColor = Color.Cyan,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Cyan,
            unfocusedLabelColor = Color.LightGray
        )
    )
}

@Composable
fun GlassCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.06f),
                RoundedCornerShape(18.dp)
            )
            .border(
                1.dp,
                Color.White.copy(alpha = 0.2f),
                RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun GradientButton(
    text: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    val scale = remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
            .shadow(
                10.dp,
                RoundedCornerShape(12.dp),
                ambientColor = colors.first(),
                spotColor = colors.first()
            )
            .background(
                Brush.horizontalGradient(colors),
                RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scale.value = 0.95f
                onClick()
                scale.value = 1f
            },
        contentAlignment = Alignment.Center
    ) {
        LocalFocusManager.current.clearFocus()
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ResultBox(result: String) {
    if (result.isEmpty()) {
        Text(
            "Result is here..",
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White.copy(alpha = 0.05f),
                    RoundedCornerShape(10.dp)
                )
                .padding(14.dp),
            textAlign = TextAlign.Center
        )
    } else {
        SelectionContainer {
            Text(
                result,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Black.copy(alpha = 0.3f),
                        RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        Color.Cyan.copy(alpha = 0.4f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun encode(input: String): String =
    input.map { (it.code + 2).toChar() }.joinToString("")

fun decode(input: String): String =
    input.map { (it.code - 2).toChar() }.joinToString("")