package com.pawan.choure.handler;

import org.springframework.integration.MessageRejectedException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public abstract class TradeHandlerSimpleJava implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println("TradeHandlerSimple Header" + message.getHeaders());
        System.out.println("TradeHandlerSimple PayLoad Hello" +message.getPayload());
        System.out.println("TradeHandlerSimple MessageToString"+message.toString());
        receiveAndAcknowledge(message.getPayload());
    }
    protected abstract void receiveAndAcknowledge(Object payload);
}
