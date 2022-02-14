package com.log.eventscommunities.presentation.screens.home.components.add_event

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.log.eventscommunities.ui.theme.picList

@ExperimentalMaterialApi
@Composable
fun TagSelector(
    selectedIndex: Int,
    onPicSelected: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        items(picList.values.toList()) { pic ->
            PicTile(
                pic = pic,
                picState = selectedIndex,
                onPicSelected = onPicSelected,
            )
        }
    }
}


@ExperimentalMaterialApi
@Composable
private fun PicTile(
    pic: Int,
    picState: Int,
    onPicSelected: (Int) -> Unit,
) {
    val header = picList.filter { it.value == pic }.toList().first().first
    Box(modifier = Modifier.padding(12.dp)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = header,
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp),
                shape = RoundedCornerShape(size = 24.dp),
                elevation = 8.dp,
                onClick = { onPicSelected(pic) },
                border = if (pic == picState) BorderStroke(2.dp, color = Color.Black) else null
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = pic),
                    contentDescription = "tag image",
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}