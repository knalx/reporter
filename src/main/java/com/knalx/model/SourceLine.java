package com.knalx.model;

import java.math.BigDecimal;

/**
 * Запись из источника транзаций
 * // в нашем случае csv
 */
public class SourceLine {
    private long pid;
    private BigDecimal pamount;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public BigDecimal getPamount() {
        return pamount;
    }

    public void setPamount(BigDecimal pamount) {
        this.pamount = pamount;
    }
}
