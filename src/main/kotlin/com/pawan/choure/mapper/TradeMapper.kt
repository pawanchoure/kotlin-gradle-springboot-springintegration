package com.pawan.choure.mapper

import com.pawan.choure.model.Trade
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

interface TradeMapper {
    @Select("SELECT * FROM Trade WHERE trade_id = #{trade_id}")
    fun findByTradeId(@Param("trade_id") trade_id: String): List<Trade>
}