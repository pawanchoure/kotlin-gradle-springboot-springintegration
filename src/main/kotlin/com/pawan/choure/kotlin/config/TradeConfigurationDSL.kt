package com.pawan.choure.kotlin.config

import com.pawan.choure.java.handler.TradeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.MessageChannels
import org.springframework.messaging.MessageChannel

@Configuration
class TradeConfigurationDSL {

    @Bean
    open fun inputChannelTradeDSL(): MessageChannel? {
        return MessageChannels.direct("inputChannelTradeDSL").get()
    }

    @Bean
    open fun tradeHandlerDSL(): TradeHandler? {
        return TradeHandler()
    }

    @Bean
    open fun integrationFlow(): IntegrationFlow? {
        return IntegrationFlows.from("inputChannelTradeDSL")
            .filter { anObject: Any? -> "World".equals(anObject) }
            .transform { str: String -> "Hello $str" }
            .handle(
                "tradeHandlerDSL",
                "handleMessage"
            ) //handle("TradeHandler","handleMessage")  // You can uncomment this for using a handler. If so, comment out the .handle(System.out::println) statement.
            //.handle(System.out::println)
            .get()
    }
}