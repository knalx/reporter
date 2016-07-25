package com.knalx.model;

/**
 * Created by knalx on 25.07.16.
 */
public class ReportLine {
    private String id;
    private CheckStatus checkStatus;
    private String errorMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CheckStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public enum CheckStatus{
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
