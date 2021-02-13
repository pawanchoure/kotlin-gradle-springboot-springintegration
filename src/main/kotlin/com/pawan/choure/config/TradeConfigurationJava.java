package com.pawan.choure.config;

import com.pawan.choure.handler.TradeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class TradeConfigurationJava {

     @Bean("inputChannelTradeJava")
    public SubscribableChannel inputChannelTradeJava() {
        return new DirectChannel();
    }

    @Bean("inputChannelTradeJavaSimple")
    public SubscribableChannel inputChannelTradeJavaSimple() {
        return new DirectChannel();
    }

    @Bean("inputChannelTradeJavaActivator")
    public SubscribableChannel inputChannelTradeJavaActivator() {
        return new DirectChannel();
    }

    @Bean
    //@ServiceActivator(inputChannel = "inputChannelTradeActivator",outputChannel = "nullChannel")
    @ServiceActivator(inputChannel = "inputChannelTradeJavaActivator")
    public MessageHandler serviceActivator(Message<?> message) {
        return new ServiceActivatingHandler(new TradeHandler());
    }

}
