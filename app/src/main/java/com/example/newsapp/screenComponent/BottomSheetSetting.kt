package com.example.newsapp.screenComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.local.model.ActiveSettingSectionEnum
import com.example.newsapp.local.model.SettingDataClass
import com.example.newsapp.remote.model.BaseViewModelContract
import com.example.newsapp.remote.viewmodel.NewsViewModel
import com.example.newsapp.util.ListUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSetting(
		newsViewModel: NewsViewModel,
		bottomSheetState: SheetState,
		onDismiss: () -> Unit,
) {

		var settingIsChange by remember {
				mutableStateOf(false)
		}

		ModalBottomSheet(
				onDismissRequest = {
						onDismiss.invoke()
						if(settingIsChange){
								newsViewModel.clearPaging()
								newsViewModel.setBaseEvent(BaseViewModelContract.BaseEvent.GetData())
						}
				},
				sheetState = bottomSheetState,
		) {
				Column(
						modifier = Modifier
								.fillMaxWidth()
								.padding(10.dp),
				) {

						Row(
								modifier = Modifier
										.fillMaxWidth()
										.padding(vertical = 10.dp),
								horizontalArrangement = Arrangement.SpaceBetween,
								verticalAlignment = Alignment.CenterVertically,
						) {
								Text(
										text = "Setting",
										modifier = Modifier,
										fontSize = 34.sp,
										fontWeight = FontWeight.Bold
								)
								Button(
										onClick = {
												newsViewModel.setBaseEvent(BaseViewModelContract.BaseEvent.InsertDataToSettingDb)
												settingIsChange = true
										},
										colors = ButtonDefaults.buttonColors(
												containerColor = Color.Transparent,
												contentColor = Color.Black,
										),
								) {
										Text(text = "Apply", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
								}
						}

						SettingPicker(
								settingTitle = ActiveSettingSectionEnum.Language,
								settingDescription = "get news for specific language",
								listItem = ListUtil.languageList,
								settings = newsViewModel.settingList,
								activeSetting = newsViewModel.activeSection,
						)

						SettingPicker(
								settingTitle = ActiveSettingSectionEnum.Country,
								settingDescription = "get news for specific Country",
								listItem = ListUtil.countryList,
								settings = newsViewModel.settingList,
								activeSetting = newsViewModel.activeSection,
						)

						SettingPicker(
								settingTitle = ActiveSettingSectionEnum.Domain,
								settingDescription = "get news for specific Domain",
								listItem = ListUtil.domainList,
								settings = newsViewModel.settingList,
								activeSetting = newsViewModel.activeSection,
						)

				}
		}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingPicker(
		settingTitle: ActiveSettingSectionEnum,
		settingDescription: String,
		listItem: List<SettingDataClass>,
		settings: MutableList<SettingDataClass>,
		activeSetting: MutableState<ActiveSettingSectionEnum>
) {

		Column {
				Text(
						text = settingTitle.name,
						fontSize = 20.sp,
						fontWeight = FontWeight.SemiBold,
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
								val colorInt = if (settings.contains(item)) Color.LightGray else Color.White
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
										border = BorderStroke(width = 1.dp, Color.Black.copy(alpha = 0.5f)),
								) {
										Text(
												text = item.name,
												fontSize = 17.sp,
												modifier = Modifier.padding(7.dp),
												color = Color.Black,
										)
								}
						}
				}
		}
}