package com.mahmoudlkashef.shipzone.searchzone.presentation.navigation

import com.mahmoudlkashef.shipzone.core.presentation.ViewNavigation

sealed class SearchZoneNavigation :ViewNavigation{
    object ToHomeScreen : SearchZoneNavigation()
}