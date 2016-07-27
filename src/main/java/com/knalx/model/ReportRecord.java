package com.knalx.model;

/**
 * Строка в файле с отчетом
 *
 * Created by knalx on 25.07.16.
 */
public class ReportRecord {
    private Long id;
    private CheckStatus checkStatus;
    private String info;

    public CheckStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum CheckStatus {
        /**
         * Запись существует и верна
         */
        OK,
        /**
         * Сумма не совпадет со значением в базе
         */
        ERROR,
        /**
         * Запись не найдена в базе
         */
        NOT_FOUND
    }
}
