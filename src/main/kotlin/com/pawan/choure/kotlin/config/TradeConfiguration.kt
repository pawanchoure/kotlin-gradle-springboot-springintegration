package com.pawan.choure.kotlin.config

import com.pawan.choure.kotlin.model.Trade
import com.pawan.choure.kotlin.service.TradeEnricher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.expression.Expression
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.integration.annotation.Filter
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.annotation.Transformer
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.transformer.ContentEnricher
import org.springframework.messaging.Message
import org.springframework.messaging.MessagingException
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.handler.annotation.Payload
import java.util.*

@Configuration
class TradeConfiguration {

    @Autowired
    private val tradeEnricher: TradeEnricher? = null

    @Bean("inputChannelTrade")
    fun inputChannelTradeJava(): SubscribableChannel? {
        return DirectChannel()
    }

    @Bean("inputChannelTradeSimple")
    fun inputChannelTradeJavaSimple(): SubscribableChannel? {
        return DirectChannel()
    }

    @Bean("inputChannelTradeActivator")
    fun inputChannelTradeJavaActivator(): SubscribableChannel? {
        return DirectChannel()
    }

    /*   @Bean
    //@ServiceActivator(inputChannel = "inputChannelTradeActivator",outputChannel = "nullChannel")
    @ServiceActivator(inputChannel = "inputChannelTradeActivator")
    public void handleMessage(Message message) throws MessagingException {
        System.out.println("TradeHandlerSimple Header" + message.getHeaders());
        System.out.println("TradeHandlerSimple PayLoad Hello" + message.getPayload());
        System.out.println("TradeHandlerSimple MessageToString" + message.toString());
    }*/

    /*   @Bean
    //@ServiceActivator(inputChannel = "inputChannelTradeActivator",outputChannel = "nullChannel")
    @ServiceActivator(inputChannel = "inputChannelTradeActivator")
    public void handleMessage(Message message) throws MessagingException {
        System.out.println("TradeHandlerSimple Header" + message.getHeaders());
        System.out.println("TradeHandlerSimple PayLoad Hello" + message.getPayload());
        System.out.println("TradeHandlerSimple MessageToString" + message.toString());
    }*/
    @ServiceActivator(inputChannel = "inputChannelTradeActivator")
    fun processMessage(message: Message<*>) {
        println("ServiceActivator Header" + message.headers)
        println("ServiceActivator PayLoad Hello" + message.payload)
        println("ServiceActivator MessageToString$message")
    }

    @Bean("tradeProcessorChannel")
    fun tradeProcessorChannel(): PublishSubscribeChannel? {
        return PublishSubscribeChannel()
    }

    @Bean("tradeProcessorQueue")
    fun tradeProcessorQueue(): QueueChannel? {
        return QueueChannel()
    }

    @Filter(inputChannel = "tradeProcessorChannel", outputChannel = "tradeProcessorQueue")
    @Throws(
        MessagingException::class
    )
    fun accept(message: Message<*>): Boolean {
        val payload = message.payload
        return payload is String
    }

    //POC Changes
    @Bean("tradeDirectChannel")
    fun tradeDirectChannel(): DirectChannel? {
        return DirectChannel()
    }

    @Bean("tradeFilterChannel")
    fun tradeFilterChannel(): DirectChannel? {
        return DirectChannel()
    }
/*
    @Bean("tradeFilteredChannel")
    public DirectChannel tradeFilteredChannel() {
        return new DirectChannel();
    }*/

    /*
    @Bean("tradeFilteredChannel")
    public DirectChannel tradeFilteredChannel() {
        return new DirectChannel();
    }*/
    @Bean("tradeEnrichChannel")
    fun tradeEnrichChannel(): DirectChannel? {
        return DirectChannel()
    }


    @Bean("tradeFilterProcessorQueue")
    fun tradeFilterProcessorQueue(): QueueChannel? {
        return QueueChannel()
    }

    @Bean("enrichedTradeProcessorQueue")
    fun enrichedTradeProcessorQueue(): QueueChannel? {
        return QueueChannel()
    }

    @Filter(
        inputChannel = "tradeDirectChannel",
        outputChannel = "tradeFilterChannel",
        discardChannel = "tradeFilterProcessorQueue"
    )
    @Throws(
        MessagingException::class
    )
    fun accept(@Payload trade: Trade): Boolean {
        return trade.sub_type.equals("Common Stock", ignoreCase = true)
    }

    @Bean //@Transformer(inputChannel = "tradeFilteredChannel",outputChannel = "tradeProcessorQueue")
    @Transformer(inputChannel = "tradeFilterChannel")
    fun contentEnricher(): ContentEnricher? {
        println("contentEnricher Started")
        val contentEnricher = ContentEnricher()
        contentEnricher.setRequestChannel(tradeEnrichChannel())
        //contentEnricher.setReplyChannel(this.tradeEnrichChannel());
        contentEnricher.outputChannel = enrichedTradeProcessorQueue()
        val propertyExpressions: MutableMap<String, Expression> = HashMap()
        val expression = SpelExpressionParser().parseExpression("payload.pnl_spn")
        propertyExpressions["pnl_spn"] = expression
        val expression2 = SpelExpressionParser().parseExpression("payload.spn")
        propertyExpressions["spn"] = expression2
        contentEnricher.setPropertyExpressions(propertyExpressions)
        println("contentEnricher Completed")
        return contentEnricher
    }

    @ServiceActivator(inputChannel = "tradeEnrichChannel")
    fun processMessage(@Payload trade: Trade): Trade? {
        println("tradeEnrichChannelActivator Before Enrichment$trade")
        val enrichedTrade = tradeEnricher!!.getEnrichedTrade(trade)
        println("tradeEnrichChannelActivator After Enrichment$trade")
        return enrichedTrade
    }
}