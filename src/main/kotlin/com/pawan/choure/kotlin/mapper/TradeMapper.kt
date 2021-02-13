package com.pawan.choure.kotlin.mapper

import com.pawan.choure.kotlin.model.Trade
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select


interface TradeMapper {
    @Select("SELECT * FROM Trade WHERE trade_id = #{trade_id}")
    fun findByTradeId(@Param("trade_id") trade_id: String): List<Trade>
}