package com.knalx.dao;


import com.knalx.model.ReportLine;
import com.knalx.service.Reporter;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Created by knalx on 25.07.16.
 */
@Component
public class ReportWriter {
    private Logger logger = LoggerFactory.getLogger(Reporter.class);

    @Value("${report.file.path}")
    private String reportFilePath;

    private ObjectMapper mapper;
    private FileWriter fileWriter;

    /**
     * Записывает отчет в файл
     */
    public void writeObjectToFile() throws IOException {
        if (this.mapper == null) {
            init();
        }
        ReportLine obj = new ReportLine();
        obj.setCheckStatus(ReportLine.CheckStatus.OK);
        obj.setId(123L);
        try {
            fileWriter.append(mapper.writeValueAsString(obj) + ",\n");
        } finally {
            fileWriter.close();
        }

    }

    /**
     * Возвращает строковое представленеи текущего времени
     * // Предполгаем что в 1 секунду не будет более 1 отчета и они не перетрутся при записи.
     */
    private String getCurrentTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        return "_" + dateTime.format(formatter);
    }

    private void init() throws IOException {
        this.fileWriter = new FileWriter(reportFilePath + getCurrentTimeString());
        this.mapper = new ObjectMapper();
    }

    public void destroy() throws IOException {
        fileWriter.close();
        mapper = null;
    }


}
