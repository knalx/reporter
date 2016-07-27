package com.knalx.dao;

import com.knalx.model.SourceTransaction;

import java.util.Optional;

/**
 * Created by knalx on 23.07.16.
 */
public interface TransactionSourceDAO {
    /**
     * Возвращает следующую транзакцию для проверки
     * @return - null если записей для проверки больше нет
     */
    public Optional<SourceTransaction> readNextLine();
}
