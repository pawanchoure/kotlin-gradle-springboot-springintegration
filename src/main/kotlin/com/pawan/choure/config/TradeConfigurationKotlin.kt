package com.pawan.choure.config

import com.pawan.choure.handler.TradeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.MessageRejectedException
import org.springframework.integration.annotation.Filter
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.handler.ServiceActivatingHandler
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessagingException
import org.springframework.messaging.SubscribableChannel

@Configuration
class TradeConfigurationKotlin {

    //SubscribableChannel Direct Channel
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
        println("ServiceActivator PayLoad Hello" + message.payload)
        println("ServiceActivator MessageToString$message")
    }

    //Publisher Subscriber Channel
    @Bean("tradeProcessorChannel")
    open fun tradeProcessorChannel(): PublishSubscribeChannel? {
        return PublishSubscribeChannel()
    }

    @Bean("tradeProcessorQueue")
    open fun tradeProcessorQueue(): QueueChannel? {
        return QueueChannel()
    }

    @Throws(MessagingException::class)
    @Filter(inputChannel = "tradeProcessorChannel", outputChannel = "tradeProcessorQueue")
    open  fun accept(message: Message<*>?): Boolean {
        val payload = message!!.payload
        return payload is String
    }
}