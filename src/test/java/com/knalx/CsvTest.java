package com.knalx;

import com.knalx.dao.ScvTransSourceDAO;
import com.knalx.model.SourceTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestConfigurationClass.class},
        initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(locations = "classpath:test.properties")
public class CsvTest {

    @Autowired
    private ScvTransSourceDAO scvTransSourceDAO;

    @Test
    public void testReadNextLine() {
        int countRecords = 0;
        Optional<SourceTransaction> sourceTransaction;
        while ((sourceTransaction = scvTransSourceDAO.readNextLine()).isPresent()) {
            sourceTransaction.get().getPid();
            countRecords++;
        }
        assertEquals(5, countRecords);

    }
}
