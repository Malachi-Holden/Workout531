package com.holden.workout531.utility

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Composable
fun Modal(onClose: ()->Unit, modifier: Modifier = Modifier, content: @Composable BoxScope.()->Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = .6f))
            .clickable(onClick = onClose)
    ){
        val modalModifier = if (modifier == Modifier){
            Modifier
                .fillMaxSize(.8f)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.Center)
        } else {
            modifier
        }
        Box(
            modifier = modalModifier.clickable(onClick = {}, interactionSource = remember {
                MutableInteractionSource()
            }, indication = null),
            content = content
        )
    }
}

@Composable
@Preview
fun ModalPreview(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = LoremIpsum().values.first())
        Modal(onClose = { /*TODO*/ }) {
            Text(text = "This is the central content")
        }
    }
}