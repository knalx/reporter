package com.knalx.dao;

import com.knalx.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * База данных с транзакицями
 */
@Component
public class TransactionsDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Просто вытягиваем транзацию по ее количество
     * Не тянем json - он нам в данном случае не нужен
     *
     * @param id - идентификатор транзакции
     */
    public Optional<Transaction> getTransactionById(Long id) {
        Optional<Transaction> transaction = Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, amount from transactions where id = ?",
                new Object[]{id},
                (rs, rowNum) -> {
                    Transaction trans = new Transaction();
                    trans.setId(rs.getInt("id"));
                    trans.setAmount(rs.getBigDecimal("amount"));
                    return trans;
                }));
        return transaction;
    }
}
