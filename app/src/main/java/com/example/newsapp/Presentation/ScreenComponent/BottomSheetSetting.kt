package com.example.newsapp.Presentation.ScreenComponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.Domain.Model.ActiveSettingSectionEnum
import com.example.newsapp.Domain.Model.BaseViewModelContract
import com.example.newsapp.Domain.Model.SettingModel
import com.example.newsapp.Presentation.ViewModel.NewsViewModel
import com.example.newsapp.constant.SettingList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BottomSheetSetting(
		newsViewModel: NewsViewModel,
		bottomSheetState: SheetState,
		onDismiss: () -> Unit,
) {

		val categoryState = remember {
				newsViewModel.activeSection
		}
		val settingsList = remember {
				newsViewModel.settingList
		}

		var settingIsChange by remember {
				mutableStateOf(false)
		}

		ModalBottomSheet(
				onDismissRequest = {
						if (settingIsChange) {
								onDismiss.invoke()
						}
				},
				sheetState = bottomSheetState,
				containerColor = MaterialTheme.colorScheme.background,
		) {
				LazyColumn(
						modifier = Modifier
								.fillMaxWidth()
								.padding(10.dp),
				) {

						stickyHeader {
								Row(
										modifier = Modifier
												.fillMaxWidth()
												.background(Color.Transparent)
												.padding(vertical = 10.dp),
										horizontalArrangement = Arrangement.SpaceBetween,
										verticalAlignment = Alignment.CenterVertically,
								) {
										Text(
												text = "Setting",
												style = MaterialTheme.typography.titleLarge,
												modifier = Modifier.background(Color.Transparent)
										)
										Button(
												onClick = {
														settingIsChange = true
														newsViewModel.setBaseEvent(
																BaseViewModelContract.BaseEvent.UpdateSettings(
																		category = categoryState.value,
																		setting = settingsList,
																)
														)
												},
												colors = ButtonDefaults.buttonColors(
														containerColor = Color.Transparent,
														contentColor = Color.Black,
												),
										) {
												Text(
														text = "Apply", fontSize = 16.sp,
														fontWeight = FontWeight.SemiBold,
														color = MaterialTheme.colorScheme.onBackground,
														modifier = Modifier.background(Color.Transparent)
												)
										}
								}
						}

						item {
								SettingPicker(
										settingTitle = ActiveSettingSectionEnum.Language,
										settingDescription = "get news for specific language",
										listItem = SettingList.languageList,
										settings = settingsList,
										activeSetting = categoryState,
								)
						}
						item {
								SettingPicker(
										settingTitle = ActiveSettingSectionEnum.Country,
										settingDescription = "get news for specific Country",
										listItem = SettingList.countryList,
										settings = settingsList,
										activeSetting = categoryState,
								)
						}
						item {
								SettingPicker(
										settingTitle = ActiveSettingSectionEnum.Domain,
										settingDescription = "get news for specific Domain",
										listItem = SettingList.domainList,
										settings = settingsList,
										activeSetting = categoryState,
								)
						}

				}
		}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingPicker(
		settingTitle: ActiveSettingSectionEnum,
		settingDescription: String,
		listItem: List<SettingModel>,
		settings: MutableList<SettingModel>,
		activeSetting: MutableState<ActiveSettingSectionEnum>,
) {
		Column {
				Text(
						text = settingTitle.name,
						fontFamily = FontFamily.Serif,
						fontSize = 22.sp,
						color = MaterialTheme.colorScheme.primary,
						modifier = Modifier
								.fillMaxWidth()
								.padding(horizontal = 5.dp),
				)
				Text(
						text = settingDescription,
						fontSize = 16.sp,
						fontWeight = FontWeight.Light,
						modifier = Modifier
								.fillMaxWidth()
								.padding(horizontal = 5.dp),
				)
				FlowRow(
						modifier = Modifier
								.fillMaxWidth()
								.padding(5.dp),
						verticalArrangement = Arrangement.spacedBy(space = 5.dp, Alignment.CenterVertically),
						horizontalArrangement = Arrangement.spacedBy(
								space = 7.dp,
								Alignment.CenterHorizontally
						),
						maxItemsInEachRow = 4
				) {
						listItem.forEach { item ->
								val colorInt by animateColorAsState(
										targetValue = if (settings.contains(item)) MaterialTheme.colorScheme.primary.copy(
												alpha = 0.4f
										) else Color.White,
										label = ""
								)
								Surface(
										onClick = {
												if (activeSetting.value != settingTitle) {
														settings.clear()
														activeSetting.value = settingTitle
												}
												if (settings.contains(item) && settings.size > 1) {
														settings.remove(item)
												} else if (settings.size < 5) {
														settings.add(item)
												}
										},
										color = colorInt,
										modifier = Modifier,
										shape = RoundedCornerShape(10.dp),
								) {
										Text(
												text = item.name,
												fontSize = 18.sp,
												fontWeight = FontWeight.Light,
												modifier = Modifier.padding(7.dp),
												color = Color.Black,
										)
								}
						}
				}
		}
}