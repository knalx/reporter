package com.knalx.dao;

import au.com.bytecode.opencsv.CSVReader;
import com.knalx.dao.SourceDAO;
import com.knalx.model.SourceLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Последовательно читает строчки из csv файла
 */
@Component
public class ScvDAO implements SourceDAO {
    private Logger logger = LoggerFactory.getLogger(ScvDAO.class);
    private CSVReader csvReader;
    private long recordsCount;

    @Value("${source.resource.path}")
    private String csvFilePath;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Возвращает null после считывании всего файла
     *
     * @return строку из файла
     * // Возможно проще удобней было бы возвращать итератор с объектами
     * // если в этот файл постоянно пишут - этот подход не сработает - необходимо дописывать
     */
    @Override
    public SourceLine readNextLine() {
        if (csvReader == null) {
            this.init();
        }
        try {
            String[] nextLine;
            nextLine = csvReader.readNext();
            if (nextLine == null || nextLine.length == 0) {
                return null;
            } else if (nextLine[0].equals("PID")) {
                //если считали заголовок
                nextLine = csvReader.readNext();
            } else if (nextLine[0].equals("TOTAL")) {
                //если считали последнюю строку
                if (Long.valueOf(nextLine[1]) != recordsCount) {
                    //возможно имеет смысл тут падать совсем
                    logger.error("WRONG records count. Check csv file", new IOException());
                } else {
                    logger.debug("Finished File reading. Correct count of read records");
                }
                this.destroy();
                return null;
            }
            recordsCount++;
            return buildSourceLine(nextLine);
        } catch (IOException e) {
            logger.error("reader reader error", e);
        }
        return null;
    }

    private SourceLine buildSourceLine(String[] line) {
        if (line != null && line.length > 0) {
            SourceLine result = new SourceLine();
            result.setPid(Long.valueOf(line[0]));
            result.setPamount(BigDecimal.valueOf(Double.valueOf(line[1])));
            return result;
        } else {
            return null;
        }

    }

    public void init() {
        recordsCount = 0;
        try {
            Resource resource = resourceLoader.getResource(csvFilePath);
            File file = resource.getFile();
            csvReader = new CSVReader(new FileReader(file), ';');
        } catch (IOException e) {
            logger.error("Init reader error", e);
        }

    }

    @PreDestroy
    public void destroy() {
        try {
            this.csvReader.close();
            this.csvReader = null;
        } catch (IOException e) {
            logger.error("Destroy reader error", e);
        }
    }

}
