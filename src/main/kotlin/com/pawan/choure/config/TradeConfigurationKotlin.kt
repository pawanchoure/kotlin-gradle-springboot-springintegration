package com.pawan.choure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.Message
import org.springframework.messaging.MessagingException
import org.springframework.messaging.SubscribableChannel

@Configuration
class TradeConfigurationKotlin {

    @Bean("inputChannelTrade")
    open fun inputChannelTrade(): SubscribableChannel? {
        return DirectChannel()
    }

    @Bean("inputChannelTradeSimple")
    open fun inputChannelTradeSimple(): SubscribableChannel? {
        return DirectChannel()
    }

    @Bean("inputChannelTradeActivator")
    open fun inputChannelTradeActivator(): SubscribableChannel? {
        return DirectChannel()
    }

    @Throws(MessagingException::class)
   // @ServiceActivator(inputChannel = "inputChannelTradeActivator",outputChannel = "nullChannel")
    @ServiceActivator(inputChannel = "inputChannelTradeActivator")
    open  fun handleMessage(message: Message<*>) {
        println("ServiceActivator Header" + message.headers)
        println("ServiceActivator PayLoad Hello" +message.payload)
        println("ServiceActivator MessageToString$message")
    }
}