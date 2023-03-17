package com.applemandi.android.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface DebounceUseCase {

     fun debounce(
        waitMs: Long = 500L,
        coroutineScope: CoroutineScope,
        func: (CharSequence) -> Unit,
        char: CharSequence
    )


    class Impl : DebounceUseCase {

        private var debounceJob: Job? = null

        override fun debounce(
            waitMs: Long,
            coroutineScope: CoroutineScope,
            func: (CharSequence) -> Unit,
            char: CharSequence
        ) {
            debounceJob?.cancel()
            debounceJob = coroutineScope.launch {
                delay(waitMs)
                func(char)
            }
        }
    }

}