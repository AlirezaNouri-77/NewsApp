package com.example.newsapp.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
		content: String,
		title: String,
		imageurl: String,
		pubDate: String,
		articleId:String,
) {

		LazyColumn(
				modifier = Modifier
						.fillMaxSize(),
		) {

				stickyHeader {
						Row(
								modifier = Modifier
										.fillMaxWidth(),
								horizontalArrangement = Arrangement.SpaceBetween,
								verticalAlignment = Alignment.CenterVertically,
						) {
								Button(
										onClick = { /*TODO*/ },
										colors = ButtonDefaults.buttonColors(
												containerColor = Color.Transparent,
										)
								) {
										Icon(
												imageVector = Icons.Default.KeyboardArrowLeft,
												contentDescription = "",
												modifier = Modifier.size(30.dp),
												tint = Color.Black,
										)
								}
								Row {
										//TODO Action Topbar
								}
						}
				}

				item {
						if (imageurl.isNotBlank()) {
								AsyncImage(
										model = imageurl,
										contentDescription = "",
										modifier = Modifier
												.fillMaxWidth()
												.height(360.dp),
										alignment = Alignment.TopCenter,
										contentScale = ContentScale.FillHeight,
								)
						}
				}

				item {
						Text(
								text = pubDate,
								fontWeight = FontWeight.Medium,
								fontSize = 13.sp,
								modifier = Modifier.padding(horizontal = 5.dp)
						)
						Spacer(
								modifier = Modifier
										.height(2.dp),
						)
						Text(
								text = title,
								fontWeight = FontWeight.SemiBold,
								fontSize = 20.sp,
								textAlign = TextAlign.Start,
								modifier = Modifier.padding(horizontal = 5.dp),
						)
						Divider(
								thickness = 1.dp,
								color = Color.Black.copy(alpha = 0.7f),
								modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
						)
						Text(
								text = content.replace("$$$", "/"),
								fontSize = 14.sp,
								fontWeight = FontWeight.Medium,
								textAlign = TextAlign.Start,
								lineHeight = 24.sp,
								modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp),
						)
				}

		}

}

@Preview(showBackground = true)
@Composable
fun perviewDetail() {
		NewsAppTheme {
				DetailScreen(
						content = "With just five days to go, the House and Senate are on a collision course as a government shutdown approaches. \\r\\nThe Senate is working to negotiate a stopgap measure on a bipartisan basis but theres",
						title = "House and Senate on collision course as shutdown nears",
						imageurl = "",
						pubDate = "2023-09-26T20:11:00Z",
						articleId = "",
				)
		}
}