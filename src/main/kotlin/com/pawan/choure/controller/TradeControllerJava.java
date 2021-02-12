package com.pawan.choure.controller;

import com.pawan.choure.handler.TradeHandlerJava;
import com.pawan.choure.handler.TradeHandlerSimpleJava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;


public class TradeControllerJava {
    @Autowired
    private SubscribableChannel inputChannelTradeJava;

    @Autowired
    private SubscribableChannel inputChannelTradeJavaSimple;

    @Autowired
    private SubscribableChannel inputChannelTradeJavaActivator;

    @GetMapping(value = "/pubSubJava")
    public void pubSub(@RequestParam(name = "name", defaultValue = "World") String name) {
        inputChannelTradeJava.send(MessageBuilder.withPayload(name).build());
        inputChannelTradeJavaSimple.send(MessageBuilder.withPayload(name).build());
        inputChannelTradeJavaActivator.send(MessageBuilder.withPayload(name).build());
    }

    @PostConstruct
    private void init() {
        System.out.println("Creating Publisher");
        inputChannelTradeJava.subscribe(new TradeHandlerJava());
        inputChannelTradeJavaSimple.subscribe(new ViewMessageHandlerJava());
    }

    private static class ViewMessageHandlerJava extends TradeHandlerSimpleJava {
        @Override
        protected void receiveAndAcknowledge(Object payload) {
            System.out.println("TradeHandlerSimple PayLoad Hello "+payload);
        }
    }

}
