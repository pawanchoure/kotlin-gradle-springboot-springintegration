package com.pawan.choure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
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

}
