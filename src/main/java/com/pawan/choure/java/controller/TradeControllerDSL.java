package com.pawan.choure.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeControllerDSL {


    private final MessageChannel inputChannelTradeDSLRequestChannel;

    public TradeControllerDSL(@Qualifier("inputChannelTradeDSL") MessageChannel inputChannelTradeDSLRequestChannel) {
        this.inputChannelTradeDSLRequestChannel = inputChannelTradeDSLRequestChannel;
    }

    @GetMapping(value = "/directPubSubDSL")
    public void directPubSubDSL(@RequestParam(name = "name", defaultValue = "World") String name) {
        inputChannelTradeDSLRequestChannel.send(MessageBuilder.withPayload(name).build());
    }
}
