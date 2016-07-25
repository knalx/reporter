package com.knalx.model;

import java.math.BigDecimal;

/**
 * Created by knalx on 25.07.16.
 */
public class Transaction {
    private Integer id;
    private BigDecimal amount;
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
