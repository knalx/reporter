package com.knalx.service;

import com.knalx.dao.ReportWriter;
import com.knalx.dao.ScvDAO;
import com.knalx.dao.SourceDAO;
import com.knalx.dao.TransactionsDao;
import com.knalx.model.ReportLine;
import com.knalx.model.SourceLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Сверяет данные в базе с транзациями с данными из источника
 */
@Component
public class Reporter {

    @Autowired
    private SourceDAO csvDAO;

//    @Autowired
//    private TransactionsDao transactionsDAO;

    @Autowired
    private ReportWriter reportWriter;

    private Logger logger = LoggerFactory.getLogger(Reporter.class);

    @PostConstruct
    public void init() {

    }

    @Scheduled(cron = "*/10 * * * * *")// каждые 10 секунду
//    @Scheduled(cron="0 * * * * *") //каждую минуту
    public void buildReport() {
        logger.info("Start report building");

        SourceLine sourceLine = csvDAO.readNextLine();
        while (sourceLine != null) {
            logger.info(String.valueOf(sourceLine.getPid()), sourceLine.getPamount().toString());
            sourceLine = csvDAO.readNextLine();
            if (sourceLine != null) {
                ReportLine reportLine = new ReportLine();
                reportLine.setId(sourceLine.getPid());
                try {
                    reportWriter.writeObjectToFile();
                } catch (IOException e) {
                    logger.error("error", e);
                }
            }
            try {
                reportWriter.destroy();
            } catch (IOException e) {
                logger.error("error while file writing", e);
            }
        }
        logger.info("Finish report building");
    }
}
