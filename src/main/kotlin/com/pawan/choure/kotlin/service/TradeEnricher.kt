package com.pawan.choure.kotlin.service

import com.pawan.choure.kotlin.model.Trade
import org.springframework.stereotype.Component

@Component
class TradeEnricher {
    fun getEnrichedTrade(trade: Trade): Trade {
        trade.pnl_spn = 0
        trade.spn = 0
        return trade
    }
}