package com.example.designs.ui_Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designs.R

@Preview(showSystemUi = true)
@Composable
fun Glassmorphose(innerPadding: PaddingValues = PaddingValues(32.dp)) {


    Box(modifier = Modifier.fillMaxSize().background(Color.Black)){
        Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription = "dw" , modifier = Modifier.fillMaxSize().blur(radius = 10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded ))
//        Column(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(0.5f).alpha(.5f)) {
//
//        }

    }

}