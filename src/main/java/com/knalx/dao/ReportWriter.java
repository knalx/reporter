package com.knalx.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knalx.model.ReportLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by knalx on 25.07.16.
 */
@Component
public class ReportWriter {

    @Autowired
    private ResourceLoader resourceLoader;

    public void writeObjectToFile(){
        ObjectMapper mapper = new ObjectMapper();
        ReportLine obj = new ReportLine();
        obj.setCheckStatus(ReportLine.CheckStatus.OK);
        obj.setId("test");
        try {
            mapper.writeValue(new File("c:\\file.json"), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
