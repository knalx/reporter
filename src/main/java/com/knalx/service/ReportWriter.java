package com.knalx.service;


import com.knalx.model.ReportRecord;
import com.knalx.service.Reporter;
import com.sun.corba.se.spi.logging.LogWrapperBase;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.logging.FileHandler;

/**
 * Записывает отчеты в файл
 * Отчет представляет собой json массив объектов со статусом по каждой транзакции
 */
@Component
public class ReportWriter {
    private Logger logger = LoggerFactory.getLogger(Reporter.class);

    @Value("${report.file.path}")
    private String reportFilePath;

    private ObjectMapper mapper;
    private FileWriter fileWriter;
    private long recordsCount = 0;

    /**
     * Записывает отчет в файл
     */
    public void writeObjectToFile(ReportRecord reportRecord) throws IOException {
        if (this.mapper == null || fileWriter == null) {
            init();
        }
        this.fileWriter.append(mapper.writeValueAsString(reportRecord) + ",\n");
        recordsCount++;
    }

    /**
     * Возвращает строковое представленеи текущего времени + uuid
     * //uuid дает уникальность наименования файла в рамках 10 секунд.
     */
    private String getCurrentTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        return "_" + dateTime.format(formatter) + "_" + UUID.randomUUID().toString().substring(0,4);
    }

    private void init() throws IOException {
        recordsCount = 0;
        this.fileWriter = new FileWriter(reportFilePath + getCurrentTimeString() + ".json");
        this.fileWriter.append("{ \"transacts\":[");
        this.mapper = new ObjectMapper();
    }

    public void destroy() throws IOException {
        //добавляем инфу об общем количестви транзакий
        this.fileWriter.append("], " + "\"totalRecords\":" + recordsCount + "}");
        fileWriter.close();
        mapper = null;
    }


}
