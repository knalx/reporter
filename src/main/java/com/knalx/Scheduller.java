package com.knalx;

import com.knalx.service.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduller {
    @Autowired
    private Reporter reporter;
    private Logger logger = LoggerFactory.getLogger(Scheduller.class);

    //@Scheduled(fixedDelay = 1000 * 10L) //каждые 10 секунд
    @Scheduled(fixedDelay = 1000 * 60L) //каждые 60 секунд
    public void buildReport() {
        logger.info("Start scheduled report building");
        reporter.buildReport();

    }
}
