package com.pawan.choure.kotlin.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.config.EnableIntegration
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableIntegration
class TradeControllerDSL constructor(@Qualifier("inputChannelTradeDSL") inputChannelTradeDSLRequestChannel: MessageChannel) {
    private var inputChannelTradeDSLRequestChannel: MessageChannel? = null

    init {
        println("First initializer block that prints")
        this.inputChannelTradeDSLRequestChannel= inputChannelTradeDSLRequestChannel
    }

    @GetMapping("directPubSubDSL")
    open fun directPubSubDSL(@RequestParam(name = "name", defaultValue = "World") name: String) {
        inputChannelTradeDSLRequestChannel!!.send(MessageBuilder.withPayload(name).build())
    }
}