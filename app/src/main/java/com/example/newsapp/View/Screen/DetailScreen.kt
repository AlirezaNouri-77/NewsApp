package com.example.newsapp.View.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.Domain.Model.Article
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.R
import com.example.newsapp.View.ViewModel.LocalViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
  localViewModel: LocalViewModel,
  backClick: () -> Unit = {},
  content: String,
  title: String,
  imageurl: String?,
  pubDate: String,
  articleId: String,
  link: String,
  description: String,
  source: String,
) {
  
  val isSavedInDb by remember {
	mutableStateOf(localViewModel.articleIdList.contains(articleId))
  }
  
  var imageState by remember {
	mutableIntStateOf(0)
  }
  
  imageState = if (isSavedInDb) {
	R.drawable.iconbookmarkfill
  } else {
	R.drawable.iconbookmark
  }
  
  LazyColumn(
	modifier = Modifier
	  .fillMaxSize(),
  ) {
	
	stickyHeader {
	  Row(
		modifier = Modifier
		  .fillMaxWidth()
		  .background(MaterialTheme.colorScheme.background),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	  ) {
		Button(
		  onClick = { backClick.invoke() },
		  colors = ButtonDefaults.buttonColors(
			containerColor = Color.Transparent,
		  ),
		) {
		  Image(
			painter = painterResource(id = R.drawable.iconback),
			contentDescription = "",
			modifier = Modifier.size(20.dp),
			colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
		  )
		}
		Row {
		  Button(
			onClick = {
			  if (!isSavedInDb) {
				localViewModel.setBaseEvent(
				  BaseViewModelContract.BaseEvent.InsertNews(
					Article(
					  article_id = articleId,
					  title = title,
					  description = description,
					  pubDate = pubDate,
					  content = content,
					  source_id = source,
					  image_url = imageurl,
					  link = link,
					)
				  )
				)
				imageState = R.drawable.iconbookmarkfill
			  } else {
				localViewModel.setBaseEvent(
				  BaseViewModelContract.BaseEvent.DeleteNews(
					articleID = articleId
				  )
				)
				imageState = R.drawable.iconbookmark
			  }
			},
			colors = ButtonDefaults.buttonColors(
			  containerColor = Color.Transparent,
			),
		  ) {
			Image(
			  painter = painterResource(id = imageState),
			  contentDescription = "",
			  modifier = Modifier.size(20.dp),
			  colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
			)
		  }
		}
	  }
	}
	
	item {
	  if (!imageurl.isNullOrEmpty()) {
		AsyncImage(
		  model = imageurl,
		  contentDescription = "",
		  modifier = Modifier
			.fillMaxWidth()
			.height(300.dp),
		  alignment = Alignment.TopCenter,
		  contentScale = ContentScale.FillBounds,
		)
	  }
	}
	
	item {
	  Text(
		text = title,
		textAlign = TextAlign.Start,
		style = MaterialTheme.typography.titleLarge,
		modifier = Modifier.padding(horizontal = 5.dp),
	  )
	  Text(
		text = source,
		style = MaterialTheme.typography.titleMedium,
		fontSize = 14.sp,
		modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
	  )
	  Text(
		text = pubDate,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		modifier = Modifier.padding(horizontal = 5.dp, vertical = 4.dp)
	  )
	  Divider(
		thickness = 1.dp,
		color = Color.Black.copy(alpha = 0.7f),
		modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
	  )
	  Text(
		text = content,
		style = MaterialTheme.typography.bodyMedium,
		modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
	  )
	}
	
  }
  
}