package com.pawan.choure.java.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


public class TradeHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println("TradeHandler Header" + message.getHeaders());
        System.out.println("TradeHandler PayLoad Hello" +message.getPayload());
        System.out.println("TradeHandler MessageToString"+message.toString());
    }
}
