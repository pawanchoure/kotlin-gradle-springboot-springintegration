package com.pawan.choure.java.service;

import com.pawan.choure.java.model.Trade;
import org.springframework.stereotype.Component;

@Component
public class TradeEnricher {

    public Trade getEnrichedTrade(Trade trade){
        trade.setPnl_spn(0000);
        trade.setSpn(0000);
        return trade;
    }
}
