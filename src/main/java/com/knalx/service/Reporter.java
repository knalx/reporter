package com.knalx.service;

import com.knalx.dao.ReportWriter;
import com.knalx.dao.ScvDAO;
import com.knalx.dao.SourceDAO;
import com.knalx.dao.TransactionsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Сверяет данные в базе с транзациями с данными из источника
 */
@Component
public class Reporter {

    @Value("${source.file.path}")
    private String name;

    @Autowired
    private SourceDAO csvDAO;

    @Autowired
    private TransactionsDao transactionsDAO;

    @Autowired
    private ReportWriter reportWriter;

    private Logger logger = LoggerFactory.getLogger( Reporter.class);


    @PostConstruct
    public void init() {
        String[] line;
        line = csvDAO.readNextLine();
        while (line != null) {
            logger.info(line[0],line[1]);
            line = csvDAO.readNextLine();
        }
        reportWriter.writeObjectToFile();
        transactionsDAO.getTransactionById(123L);
    }
}
