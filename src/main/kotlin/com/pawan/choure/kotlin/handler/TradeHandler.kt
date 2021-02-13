package com.pawan.choure.kotlin.handler

import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.handler.AbstractMessageHandler
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessagingException
import org.springframework.stereotype.Component

@Component
open class TradeHandler : MessageHandler {
    @Throws(MessagingException::class)
    //@ServiceActivator(inputChannel = "inputChannelTradeServiceActivator",outputChannel = "nullChannel")  No Use
    open override fun handleMessage(message: Message<*>) {
        println("TradeHandler Header" + message.headers)
        println("TradeHandler PayLoad Hello" +message.payload)
        println("TradeHandler MessageToString$message")
    }
}