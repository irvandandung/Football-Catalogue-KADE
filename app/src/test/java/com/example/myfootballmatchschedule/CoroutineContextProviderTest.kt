package com.example.myfootballmatchschedule

import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class CoroutineContextProviderTest : CoroutineContextProvider() {
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined
}