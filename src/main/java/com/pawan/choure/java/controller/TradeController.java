package com.pawan.choure.java.controller;

import com.pawan.choure.java.handler.TradeHandler;
import com.pawan.choure.java.handler.TradeHandlerSimple;
import com.pawan.choure.java.mapper.TradeMapper;
import com.pawan.choure.java.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@EnableIntegration
public class TradeController {

    private final Timer timer = new Timer();

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private SubscribableChannel inputChannelTrade;

    @Autowired
    private SubscribableChannel inputChannelTradeSimple;

    @Autowired
    private SubscribableChannel inputChannelTradeActivator;

    @Autowired
    private  PublishSubscribeChannel tradeProcessorChannel;

    @Autowired
    private QueueChannel tradeProcessorQueue;


    @GetMapping({"/trade"})
    public Trade trade(@RequestParam(value = "tradeId",defaultValue = "TE123")  String tradeId) {
      return tradeMapper.findByTradeId(tradeId);
    }
    @GetMapping(value = "/directPubSub")
    public void directPubSub(@RequestParam(name = "name", defaultValue = "World") String name) {
        inputChannelTrade.send(MessageBuilder.withPayload(name).build());
        inputChannelTradeSimple.send(MessageBuilder.withPayload(name).build());
       inputChannelTradeActivator.send(MessageBuilder.withPayload(name).build());
    }



    @GetMapping({"/filter"})
    public void filter(@RequestParam(value = "name",defaultValue = "World")  String name) {
        tradeProcessorChannel.send(MessageBuilder.withPayload(name).build());
        tradeProcessorChannel.send(MessageBuilder.withPayload(name).build());
        tradeProcessorChannel.send(MessageBuilder.withPayload(1).build());

    }

    @PostConstruct
    private void init() {
        System.out.println("Register Subscribers");
        inputChannelTrade.subscribe(new TradeHandler());
        inputChannelTradeSimple.subscribe(new ViewMessageHandler());
        this.start();

    }
    private void start() {
        timer.schedule(new TimerTask() {
            public void run() {
                checkForNotifications();
            }
        }, 3000, 3000);
    }

    private void checkForNotifications() {
        GenericMessage<?> message = (GenericMessage<?>) tradeProcessorQueue.receive(1000);
        if (message != null) {
            System.out.println("Filter Messages " + message);

        }
    }



    private static class ViewMessageHandler extends TradeHandlerSimple {
        @Override
        protected void receiveAndAcknowledge(Object payload) {
            System.out.println("TradeHandlerSimple PayLoad Hello "+payload);
        }
    }




}
