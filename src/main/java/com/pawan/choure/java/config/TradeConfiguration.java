package com.pawan.choure.java.config;


import com.pawan.choure.java.model.Trade;
import com.pawan.choure.java.service.TradeEnricher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.transformer.ContentEnricher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TradeConfiguration {

    @Autowired
    private TradeEnricher tradeEnricher;

    @Bean("inputChannelTrade")
    public SubscribableChannel inputChannelTradeJava() {
        return new DirectChannel();
    }

    @Bean("inputChannelTradeSimple")
    public SubscribableChannel inputChannelTradeJavaSimple() {
        return new DirectChannel();
    }

    @Bean("inputChannelTradeActivator")
    public SubscribableChannel inputChannelTradeJavaActivator() {
        return new DirectChannel();
    }

 /*   @Bean
    //@ServiceActivator(inputChannel = "inputChannelTradeActivator",outputChannel = "nullChannel")
    @ServiceActivator(inputChannel = "inputChannelTradeActivator")
    public void handleMessage(Message message) throws MessagingException {
        System.out.println("TradeHandlerSimple Header" + message.getHeaders());
        System.out.println("TradeHandlerSimple PayLoad Hello" + message.getPayload());
        System.out.println("TradeHandlerSimple MessageToString" + message.toString());
    }*/

    @ServiceActivator(inputChannel = "inputChannelTradeActivator")
    public void processMessage(Message<?> message) {
        System.out.println("ServiceActivator Header" + message.getHeaders());
        System.out.println("ServiceActivator PayLoad Hello" + message.getPayload());
        System.out.println("ServiceActivator MessageToString" + message.toString());
    }

    @Bean({"tradeProcessorChannel"})
    public PublishSubscribeChannel tradeProcessorChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean({"tradeProcessorQueue"})
    public QueueChannel tradeProcessorQueue() {
        return new QueueChannel();
    }

    @Filter(inputChannel = "tradeProcessorChannel", outputChannel = "tradeProcessorQueue")
    public boolean accept(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        return payload instanceof String;
    }

    //POC Changes
    @Bean("tradeDirectChannel")
    public DirectChannel tradeDirectChannel() {
        return new DirectChannel();
    }

    @Bean("tradeFilterChannel")
    public DirectChannel tradeFilterChannel() {
        return new DirectChannel();
    }
/*
    @Bean("tradeFilteredChannel")
    public DirectChannel tradeFilteredChannel() {
        return new DirectChannel();
    }*/

    @Bean("tradeEnrichChannel")
    public DirectChannel tradeEnrichChannel() {
        return new DirectChannel();
    }


    @Bean({"tradeFilterProcessorQueue"})
    public QueueChannel tradeFilterProcessorQueue() {
        return new QueueChannel();
    }

    @Bean({"enrichedTradeProcessorQueue"})
    public QueueChannel enrichedTradeProcessorQueue() {
        return new QueueChannel();
    }

    @Filter(inputChannel = "tradeDirectChannel", outputChannel = "tradeFilterChannel",discardChannel = "tradeFilterProcessorQueue")
    public boolean accept(@Payload Trade trade) throws MessagingException {
        return trade.getSub_type().equalsIgnoreCase("Common Stock");
    }

    @Bean
    //@Transformer(inputChannel = "tradeFilteredChannel",outputChannel = "tradeProcessorQueue")
    @Transformer(inputChannel = "tradeFilterChannel")
    public ContentEnricher contentEnricher() {
        System.out.println("contentEnricher Started");
        ContentEnricher contentEnricher = new ContentEnricher();
        contentEnricher.setRequestChannel(this.tradeEnrichChannel());
        //contentEnricher.setReplyChannel(this.tradeEnrichChannel());
        contentEnricher.setOutputChannel(this.enrichedTradeProcessorQueue());
        Map<String, Expression> propertyExpressions= new HashMap<>();
        Expression expression = new SpelExpressionParser().parseExpression("payload.pnl_spn");
        propertyExpressions.put("pnl_spn",expression);
        Expression expression2 = new SpelExpressionParser().parseExpression("payload.spn");
        propertyExpressions.put("spn",expression2);
        contentEnricher.setPropertyExpressions(propertyExpressions);
        System.out.println("contentEnricher Completed");
        return contentEnricher;
    }

    @ServiceActivator(inputChannel = "tradeEnrichChannel")
    public Trade processMessage(@Payload Trade trade) {
        System.out.println("tradeEnrichChannelActivator Before Enrichment" + trade.toString());
        Trade enrichedTrade=tradeEnricher.getEnrichedTrade(trade);
        System.out.println("tradeEnrichChannelActivator After Enrichment" + trade.toString());
        return enrichedTrade;
    }


}
