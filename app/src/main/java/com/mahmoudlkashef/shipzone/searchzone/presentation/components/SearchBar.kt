package com.mahmoudlkashef.shipzone.searchzone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.mahmoudlkashef.shipzone.core.presentation.noRippleClickable
import kotlinx.coroutines.android.awaitFrame

@Composable
fun SearchBarComponent(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onSearchClicked: () -> Unit = {},
    placeholder: String = "",
    leadingIcon: Painter? = null,
    isEnabled: Boolean = true,
    autoFocused: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    iconColor: Color = Color.Unspecified,
    onClearSearchClicked: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    if (autoFocused) {
        LaunchedEffect(focusRequester) {
            awaitFrame()
            focusRequester.requestFocus()
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .height(55.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
            ) {
                leadingIcon?.let { icon ->
                    Box(modifier = Modifier.padding(start = 16.dp, end = 24.dp)) {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            tint = iconColor,
                        )
                    }
                }

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { onQueryChange(it) },
                    enabled = isEnabled,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused },
                    singleLine = true,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            onSearchClicked()
                        },
                    ),
                    cursorBrush = SolidColor(textColor),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = TextStyle.Default.copy(color = Color.LightGray),
                            )
                        }
                        innerTextField()
                    },
                )

                Box(modifier = Modifier.padding(start = 24.dp)) {
                    Icon(
                        imageVector = if (searchQuery.isNotEmpty()) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier
                            .noRippleClickable { if (searchQuery.isNotEmpty()) onClearSearchClicked() },
                    )
                }
            }
        }
    }
}