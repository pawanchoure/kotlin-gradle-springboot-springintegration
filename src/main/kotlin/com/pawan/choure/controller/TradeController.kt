package com.pawan.choure.controller


import com.pawan.choure.handler.TradeHandler
import com.pawan.choure.handler.TradeHandlerSimple
import com.pawan.choure.mapper.TradeMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.config.EnableIntegration
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct


@RestController
@EnableIntegration
class TradeController {
    @Autowired
    private val tradeMapper: TradeMapper? = null

    @Autowired
    private val inputChannelTrade: SubscribableChannel? = null

    @Autowired
    private val inputChannelTradeSimple: SubscribableChannel? = null

    @Autowired
    private val inputChannelTradeActivator: SubscribableChannel? = null

    @GetMapping("/trade")
    fun trade(@RequestParam(value = "tradeId", defaultValue = "TE123") name: String) =
        this.tradeMapper?.findByTradeId("TE123")


    @GetMapping("/directPubSub")
    fun pubSub(@RequestParam(value = "name", defaultValue = "World") name: String) {
        inputChannelTrade?.send(MessageBuilder.withPayload(name).build())
        inputChannelTradeSimple?.send(MessageBuilder.withPayload(name).build())
        inputChannelTradeActivator?.send(MessageBuilder.withPayload(name).build())
    }

    @PostConstruct
    private fun init() {
        println("Register Subscribers")
        inputChannelTrade?.subscribe(TradeHandler());
        inputChannelTradeSimple?.subscribe(ViewMessageHandler());
    }

    private class ViewMessageHandler : TradeHandlerSimple() {
        override fun receiveAndAcknowledge(status: Any) {
            println("TradeHandlerSimple PayLoad Hello $status")
        }
    }

}