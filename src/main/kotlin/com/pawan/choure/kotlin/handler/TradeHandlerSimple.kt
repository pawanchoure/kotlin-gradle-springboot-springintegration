package com.pawan.choure.kotlin.handler

import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessagingException

abstract class TradeHandlerSimple : MessageHandler {

    @Throws(MessagingException::class)
    open override fun handleMessage(message: Message<*>) {
        println("TradeHandlerSimple Header" + message.headers)
        println("TradeHandlerSimple PayLoad Hello" +message.payload)
        println("TradeHandlerSimple MessageToString$message")
        val payload = message.payload
        receiveAndAcknowledge(payload)
    }

    protected abstract fun receiveAndAcknowledge(status: Any)
}

