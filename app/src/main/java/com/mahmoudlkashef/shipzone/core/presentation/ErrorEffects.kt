package com.mahmoudlkashef.shipzone.core.presentation

sealed class ErrorEffects {
    data class ShowSnackbar(val message: String) : ErrorEffects()
    object ShowBlockingError : ErrorEffects()
} 