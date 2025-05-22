package com.mahmoudlkashef.shipzone.searchzone.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudlkashef.shipzone.R
import com.mahmoudlkashef.shipzone.core.presentation.ErrorEffects
import com.mahmoudlkashef.shipzone.core.presentation.ViewIntent
import com.mahmoudlkashef.shipzone.core.presentation.ViewNavigation
import com.mahmoudlkashef.shipzone.core.presentation.noRippleClickable
import com.mahmoudlkashef.shipzone.searchzone.presentation.components.BlockingErrorScreen
import com.mahmoudlkashef.shipzone.searchzone.presentation.components.CustomLoadingProgress
import com.mahmoudlkashef.shipzone.searchzone.presentation.components.ErrorSnackbar
import com.mahmoudlkashef.shipzone.searchzone.presentation.components.SearchBarComponent
import com.mahmoudlkashef.shipzone.searchzone.presentation.intent.SearchZoneIntent
import com.mahmoudlkashef.shipzone.searchzone.presentation.navigation.SearchZoneNavigation
import com.mahmoudlkashef.shipzone.searchzone.presentation.viewModel.SearchZoneViewModel
import com.mahmoudlkashef.shipzone.searchzone.presentation.viewState.SearchZoneViewState

@Composable
fun SearchZoneScreen(
    viewModel: SearchZoneViewModel = hiltViewModel(),
    toHomeScreen:()->Unit
) {
    val state by viewModel.state.collectAsState()

    Content(state = state) {
        viewModel.sendIntent(it)
    }

    HandleNavigationEffect(viewModel){
        when(it){
            is SearchZoneNavigation.ToHomeScreen ->toHomeScreen()
        }
    }

    HandleErrorEffect(viewModel)

}

@Composable
fun Content(
    state: SearchZoneViewState,
    executeIntent: (SearchZoneIntent) -> Unit
) {

    LaunchedEffect(Unit) {
        executeIntent(SearchZoneIntent.OnScreenOpened)
    }

    if (state.isLoading) {
        CustomLoadingProgress(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LanguageDropdown(modifier = Modifier.fillMaxWidth()){
                executeIntent(SearchZoneIntent.OnLanguageChanged(it))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.choose_delivery_area),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle.Default.copy(
                        Color.Black
                    )
                )
                Icon(imageVector = Icons.Default.Close, contentDescription = "")
            }

            SearchBarComponent(
                placeholder = stringResource(R.string.city_area),
                onQueryChange = {
                    executeIntent(SearchZoneIntent.OnSearchQueryChanged(it))
                },
                onClearSearchClicked = {
                    executeIntent(SearchZoneIntent.OnClearSearchQuery)
                },
                onSearchClicked = {},
                isEnabled = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier =
                    Modifier
                        .fillMaxWidth(),
                searchQuery = state.searchQuery,
            )

            LazyColumn {
                items(state.cities, key = { it.hashCode() }) { city ->
                    ExpandableRowComponent(title = city.name) {
                        repeat(city.districts.size) { index ->
                            DistrictItem(
                                districtName = city.districts[index].name,
                                isCovered = city.districts[index].pickupAvailability
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
fun ExpandableRowComponent(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = TextStyle.Default.copy(Color.Black),
    content: @Composable () -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .noRippleClickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = titleStyle,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(24.dp)
                        .rotate(if (isExpanded) 180f else 0f),
                tint = Color.Unspecified,
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                content()
            }
        }
    }
}

@Composable
fun DistrictItem(districtName: String, isCovered: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = districtName,
            color = if (isCovered) Color.Black else Color.Gray.copy(alpha = 0.5f),
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        if (!isCovered)
            Text(
                text = stringResource(R.string.uncovered),
                color = Color.Gray.copy(alpha = 0.5f),
                fontSize = 14.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
    }
}


@Composable
fun HandleErrorEffect(viewModel: SearchZoneViewModel) {
    val effect by viewModel.errorEffect.collectAsState(initial = null)

    when (effect) {
        is ErrorEffects.ShowBlockingError -> BlockingErrorScreen(onRetry = {
            viewModel.retryLastIntent()
            viewModel.resetError()
        })

        is ErrorEffects.ShowSnackbar -> ErrorSnackbar((effect as ErrorEffects.ShowSnackbar).message)
        else -> {}
    }
}

@Composable
fun LanguageDropdown(
    modifier: Modifier = Modifier,
    onLanguageSelected: (String) -> Unit,
    ) {
    val languages = listOf(
        Pair(stringResource(R.string.english),"en"),
        Pair(stringResource(R.string.arabic),"ar"),
    )
    var expanded by remember { mutableStateOf(false) }

    Box(modifier, contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.change_app_language),
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .noRippleClickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { lang ->
                DropdownMenuItem(
                    text = { Text(lang.first) },
                    onClick = {
                        expanded = false
                        onLanguageSelected(lang.second)
                    }
                )
            }
        }
    }
}

@Composable
fun HandleNavigationEffect(
    viewModel:SearchZoneViewModel,
    onNavigationTriggered: (navigationEffect: ViewNavigation?) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.state.collect {
            onNavigationTriggered(it.navigation)
        }
    }
}