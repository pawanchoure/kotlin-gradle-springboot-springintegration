package com.pawan.choure.kotlin.controller


import com.pawan.choure.kotlin.handler.TradeHandler
import com.pawan.choure.kotlin.handler.TradeHandlerSimple
import com.pawan.choure.kotlin.mapper.TradeMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.messaging.Message
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.annotation.PostConstruct


@RestController
@EnableIntegration
class TradeController {

    private val timer = Timer()

    @Autowired
    private val tradeMapper: TradeMapper? = null

    @Autowired
    private val inputChannelTrade: SubscribableChannel? = null

    @Autowired
    private val inputChannelTradeSimple: SubscribableChannel? = null

    @Autowired
    private val inputChannelTradeActivator: SubscribableChannel? = null

    @Autowired
    private val tradeProcessorChannel: PublishSubscribeChannel? = null

    @Autowired
    private val tradeProcessorQueue: QueueChannel? = null

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
        //val message = tradeProcessorQueue?.receive(1000)
        //print(message)
        start()
    }

    private class ViewMessageHandler : TradeHandlerSimple() {
        override fun receiveAndAcknowledge(status: Any) {
            println("TradeHandlerSimple PayLoad Hello $status")
        }
    }

    @GetMapping("/filter")
    fun filter(@RequestParam(value = "name", defaultValue = "World") name: String) {
        tradeProcessorChannel?.send(MessageBuilder.withPayload(name).build())
        tradeProcessorChannel?.send(MessageBuilder.withPayload(name).build())
        tradeProcessorChannel?.send(MessageBuilder.withPayload(1).build())

    }

    private fun start() {
        /* Represents long-running process thread */
        timer.schedule(object : TimerTask() {
            override fun run() {
                checkForNotifications()
            }
        }, 3000, 3000)
    }

    private fun checkForNotifications() {
        /* Check queue for notifications that the software needs to be updated */
        val message: Message<*>? = tradeProcessorQueue?.receive(1000)
        if (message != null) {
            println("Filter Messages $message")
        }


    }


}