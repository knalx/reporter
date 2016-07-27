package com.knalx.model;

import java.math.BigDecimal;

/**
 * Json данные не мапим - они нам не интересны
 * Created by knalx on 25.07.16.
 */
public class Transaction {
    private Integer id;
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(Integer id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

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


}
