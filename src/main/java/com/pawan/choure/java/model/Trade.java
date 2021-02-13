package com.pawan.choure.java.model;

import java.util.Date;
import java.util.Objects;


public class Trade {

    String trade_id;
    String type;
    String sub_type;
    Integer pnl_spn;
    Integer spn;
    Integer quantity;
    Date trade_date;
    Double price;

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public Integer getPnl_spn() {
        return pnl_spn;
    }

    public void setPnl_spn(Integer pnl_spn) {
        this.pnl_spn = pnl_spn;
    }

    public Integer getSpn() {
        return spn;
    }

    public void setSpn(Integer spn) {
        this.spn = spn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(Date trade_date) {
        this.trade_date = trade_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        return Objects.equals(trade_id, trade.trade_id);
    }

    @Override
    public int hashCode() {
        return trade_id != null ? trade_id.hashCode() : 0;
    }
}
