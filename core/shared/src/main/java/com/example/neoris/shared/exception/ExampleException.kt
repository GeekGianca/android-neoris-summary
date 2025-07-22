package com.example.neoris.shared.exception

sealed class ExampleException(override val message: String) : Exception(message) {
    override fun toString(): String = message
}

class TimeoutException(mess: String = "Oh oh, parece que no pudimos completar la operación.") : ExampleException(mess)
class CommonException(mess: String = "Oh! oh!, algo paso, pero no te preocupes intenta nuevamente.") :
    ExampleException(mess)