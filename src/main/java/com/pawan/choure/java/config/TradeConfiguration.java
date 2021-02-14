package com.pawan.choure.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.*;

@Configuration
public class TradeConfiguration {

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
    public void processMessage(Message<?> message){
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


}
