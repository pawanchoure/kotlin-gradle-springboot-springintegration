package com.pawan.choure.java.mapper;

import com.pawan.choure.java.model.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Mapper
public interface TradeMapper {

    @Select({"SELECT * FROM Trade WHERE trade_id = #{trade_id}"})
    Trade findByTradeId(@Param("trade_id") String trade_id);

    @Select({"SELECT * FROM Trade"})
    List<Trade> getAllTrades();
}
