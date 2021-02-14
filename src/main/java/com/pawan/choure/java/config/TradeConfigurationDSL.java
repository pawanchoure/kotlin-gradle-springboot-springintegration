package com.pawan.choure.java.config;

import com.pawan.choure.java.handler.TradeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
public class TradeConfigurationDSL {

    @Bean
    public MessageChannel inputChannelTradeDSL() {
        return MessageChannels.direct("inputChannelTradeDSL").get();
    }

    @Bean
    public TradeHandler tradeHandler(){
        return new TradeHandler();
    }
    @Bean
    public IntegrationFlow integrationFlow(){
        return IntegrationFlows.from("inputChannelTradeDSL")
                .filter("World"::equals)
                .transform("Hello "::concat)
                .handle("tradeHandler","handleMessage")
                //handle("TradeHandler","handleMessage")  // You can uncomment this for using a handler. If so, comment out the .handle(System.out::println) statement.
                //.handle(System.out::println)
                .get();
    }

}
