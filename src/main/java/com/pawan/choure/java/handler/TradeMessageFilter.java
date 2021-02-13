package com.pawan.choure.java.handler;

import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

public class TradeMessageFilter implements MessageSelector {
    @Override
    public boolean accept(Message<?> message) {
        Object payload =message.getPayload();
        if(payload instanceof String){
            return true;
        }else {
            throw  new MessageRejectedException(message,"Unknown Data Type has been received");
        }
    }
}
