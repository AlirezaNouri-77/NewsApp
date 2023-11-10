package com.example.newsapp.View.ScreenComponent

import NewsAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.newsapp.View.util.shimmerEffect

@Composable
fun LoadingScreenShimmer(
  modifier: Modifier
) {
  Column(
	modifier = modifier
	  .scrollable(
		state = rememberScrollState(),
		orientation = Orientation.Vertical,
		enabled = false,
	  ),
	horizontalAlignment = Alignment.CenterHorizontally,
	verticalArrangement = Arrangement.Top,
  ) {
	repeat(8) {
	  LoadingShimmerItem()
	}
  }
}

@Composable
fun LoadingShimmerItem() {
  Surface(onClick = { }) {
	ConstraintLayout(
	  Modifier
		.fillMaxWidth(),
	) {
	  
	  val (title,
		description,
		image,
		bookmarkIcon,
		source,
		pubDate) = createRefs()
	  
	  val startGuideLine = createGuidelineFromStart(10.dp)
	  val absouluteEnd = createGuidelineFromAbsoluteRight(10.dp)
	  val topGuideLine = createGuidelineFromTop(10.dp)
	  val bottomGuideLine = createGuidelineFromBottom(0.2f)
	  val endGuideLine = createGuidelineFromEnd(0.3f)
	  
	  Spacer(
		modifier = Modifier
		  .size(60.dp, 15.dp)
		  .clip(RoundedCornerShape(15.dp))
		  .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
		  .shimmerEffect()
		  .constrainAs(source) {
			top.linkTo(parent.top, margin = 5.dp)
			start.linkTo(startGuideLine)
		  },
	  )
	  
	  Spacer(
		modifier = Modifier
		  .size(150.dp, 40.dp)
		  .clip(RoundedCornerShape(15.dp))
		  .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
		  .shimmerEffect()
		  .constrainAs(title) {
			top.linkTo(source.bottom, margin = 3.dp)
			start.linkTo(startGuideLine)
			end.linkTo(endGuideLine)
			width = Dimension.fillToConstraints
		  }
	  )
	  
	  Spacer(
		modifier = Modifier
		  .size(150.dp, 40.dp)
		  .clip(RoundedCornerShape(15.dp))
		  .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
		  .shimmerEffect()
		  .constrainAs(description) {
			top.linkTo(title.bottom, margin = 3.dp)
			start.linkTo(startGuideLine)
			end.linkTo(endGuideLine, margin = 25.dp)
			bottom.linkTo(bottomGuideLine)
			width = Dimension.fillToConstraints
		  },
	  )
	  
	  Spacer(
		modifier = Modifier
		  .size(80.dp, 20.dp)
		  .clip(RoundedCornerShape(15.dp))
		  .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
		  .shimmerEffect()
		  .constrainAs(pubDate) {
			top.linkTo(bottomGuideLine, margin = 3.dp)
			start.linkTo(startGuideLine)
		  }
	  )
	  
	  Spacer(
		modifier = Modifier
		  .clip(RoundedCornerShape(15.dp))
		  .aspectRatio(1f)
		  .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
		  .shimmerEffect()
		  .constrainAs(image) {
			top.linkTo(topGuideLine, margin = 3.dp)
			start.linkTo(endGuideLine, margin = 5.dp)
			end.linkTo(parent.end, margin = 15.dp)
			bottom.linkTo(bottomGuideLine)
			width = Dimension.fillToConstraints
		  },
	  )
	  
	  Spacer(
		modifier = Modifier
		  .size(20.dp)
		  .clip(RoundedCornerShape(10.dp))
		  .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
		  .shimmerEffect()
		  .constrainAs(bookmarkIcon) {
			bottom.linkTo(parent.bottom, margin = 5.dp)
			top.linkTo(bottomGuideLine, margin = 3.dp)
			end.linkTo(parent.absoluteRight, margin = 15.dp)
		  },
	  )
	  
	}
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsListItemss() {
  NewsAppTheme {
	LoadingScreenShimmer(modifier = Modifier.fillMaxSize())
  }
}