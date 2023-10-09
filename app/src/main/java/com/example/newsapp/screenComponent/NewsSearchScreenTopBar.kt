package com.example.newsapp.screenComponent

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsSearchViewModel
import com.example.newsapp.screen.SearchScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun NewsSearchScreenTopBar(
		onSearch : (String) -> Unit
) {

		var textFiledValue by remember {
				mutableStateOf("")
		}

		LaunchedEffect(key1 = textFiledValue) {
				snapshotFlow {
						textFiledValue
				}.debounce(700L).collectLatest {
						onSearch.invoke(it)
				}
		}

		TopAppBar(
				title = {},
				colors = TopAppBarDefaults.smallTopAppBarColors(),
				actions = {
						OutlinedTextField(
								value = textFiledValue,
								onValueChange = { textFiledValue = it },
								modifier = Modifier
										.fillMaxWidth()
										.padding(horizontal = 5.dp, vertical = 2.dp),
								placeholder = { Text(text = "something like Apple, Sony, USA ...") },
								colors = TextFieldDefaults.textFieldColors(
										textColor = Color.Black,
										placeholderColor = Color.Black.copy(alpha = 0.5f),
										disabledIndicatorColor = Color.Black.copy(alpha = 0.5f),
										unfocusedIndicatorColor = Color.Black.copy(alpha = 0.5f),
										focusedIndicatorColor = Color.Black,
										containerColor = Color.Transparent,
								),
								maxLines = 1,
								leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
						)
				}
		)
}
