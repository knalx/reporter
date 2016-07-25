package com.knalx.dao;

import au.com.bytecode.opencsv.CSVReader;
import com.knalx.dao.SourceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Последовательно читает строчки из csv файла
 */
@Component
public class ScvDAO implements SourceDAO {

    private Logger logger = LoggerFactory.getLogger(ScvDAO.class);
    private CSVReader csvReader;
    private long recordsCount;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        recordsCount = 0;
        try {
            Resource resource = resourceLoader.getResource("classpath:ptxs.csv");
            File file = resource.getFile();
            csvReader = new CSVReader(new FileReader(file), ';');
        } catch (IOException e) {
            logger.error("Init reader error", e);
        }

    }

    /**
     * Возвращает null после считывании всего файла
     * @return строку из файла
     * // Возможно проще удобней было бы возвращать итератор с объектами
     */
    @Override
    public String[] readNextLine() {
        String[] nextLine = new String[0];
        try {
            nextLine = csvReader.readNext();
            //если считали заголовок
            if (nextLine==null) {
                return null;
            } else if (nextLine[0].equals("PID")) {
                nextLine = csvReader.readNext();
            } else if (nextLine[0].equals("TOTAL")) {
                if (Long.valueOf(nextLine[1]) != recordsCount) {
                    logger.error("WRONG records count. Check csv file", new IOException());
                }
            }
            recordsCount++;
            return nextLine;
        } catch (IOException e) {
            logger.error("reader reader error", e);
        }
        return nextLine;
    }

    @PreDestroy
    public void destroy() {
        try {
            csvReader.close();
        } catch (IOException e) {
            logger.error("Destroy reader error", e);
        }
    }

}
