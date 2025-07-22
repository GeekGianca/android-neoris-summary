package com.example.neoris.shared.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val niaDispatcher: ExampleDispatchers)

enum class ExampleDispatchers {
    Default,
    IO,
}