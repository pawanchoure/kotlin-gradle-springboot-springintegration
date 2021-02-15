package com.pawan.choure.java;

import com.pawan.choure.java.controller.TradeController;
import com.pawan.choure.java.model.Trade;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TradeController.class)

public class TradeControllerTest {

    @MockBean
    TradeController tradeController;

    @Autowired
    TradeController tradeController1;

    @Autowired
    private MockMvc mockMvc;

    Trade trade = new Trade("TE123", "Equity", "Common Stock", 123, 1234, LocalDate.of(2021, 2, 15), 10.18, 10);

    @Test
    public void trade() throws Exception {
        Mockito.when(tradeController.trade(Mockito.anyString())).thenReturn(trade);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/trade").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{\"trade_id\":\"TE123\",\"type\":\"Equity\",\"sub_type\":\"Common Stock\",\"pnl_spn\":123,\"spn\":1234,\"quantity\":10,\"trade_date\":\"2021-02-15\",\"price\":10.18}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}


