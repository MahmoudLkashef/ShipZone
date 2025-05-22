package com.mahmoudlkashef.shipzone.testdispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class TestDispatcherExtension : AfterAllCallback, BeforeAllCallback {
    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }
}
