package com.knalx.service;

import com.knalx.dao.TransactionSourceDAO;
import com.knalx.dao.TransactionsDao;
import com.knalx.model.ReportRecord;
import com.knalx.model.SourceTransaction;
import com.knalx.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * Сверяет данные в базе с транзациями с данными из источника
 */
@Component
public class Reporter {

    @Autowired
    private TransactionSourceDAO scvTransSourceDAO;

    @Autowired
    private TransactionsDao transactionsDAO;

    @Autowired
    private ReportWriter reportWriter;

    private Logger logger = LoggerFactory.getLogger(Reporter.class);

    /**
     * Построить отчет о сверке транзакий из источника
     */
    public void buildReport() {
        Optional<SourceTransaction> sourceTransaction;
        while ((sourceTransaction = scvTransSourceDAO.readNextLine()).isPresent()) {
            try {
                reportWriter.writeObjectToFile(
                        buildReportRecord(
                                sourceTransaction.get(),
                                transactionsDAO.getTransactionById(sourceTransaction.get().getPid())
                        )
                );

            } catch (IOException e) {
                logger.error("Error while report creating writing", e);
            }
        }
        try {
            reportWriter.destroy();
            logger.info("Finished report building");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ReportRecord buildReportRecord(SourceTransaction source, Optional<Transaction> transFromDb) {
        ReportRecord reportRecord = new ReportRecord();
        reportRecord.setId(source.getPid());
        if(transFromDb.isPresent()) {
            if(transFromDb.get().getAmount().equals(source.getPamount())) {
                reportRecord.setCheckStatus(ReportRecord.CheckStatus.OK);
                reportRecord.setInfo("Correct");
            } else {
                reportRecord.setCheckStatus(ReportRecord.CheckStatus.ERROR);
                reportRecord.setInfo("Wrong amount: " + source.getPamount() + ", in DB : " + transFromDb.get().getAmount());
            }
        } else {
            reportRecord.setCheckStatus(ReportRecord.CheckStatus.NOT_FOUND);
            reportRecord.setInfo("No transaction with this Id in DB");
        }
        return reportRecord;
    }
}
