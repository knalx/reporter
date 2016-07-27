package com.knalx;

import com.knalx.model.ReportRecord;
import com.knalx.model.SourceTransaction;
import com.knalx.model.Transaction;
import com.knalx.service.Reporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
public class ReporterTest {

    @Autowired
    private Reporter reporter;

    @Test
    public void buildReportRecordTest() {
        Optional<Transaction> optTransaction = Optional.of(new Transaction(1, new BigDecimal(10)));
        SourceTransaction correctSource = new SourceTransaction(1, BigDecimal.valueOf(10));
        SourceTransaction wrongAmountSource = new SourceTransaction(1, BigDecimal.valueOf(11));
        SourceTransaction wrongIdSource = new SourceTransaction(2, BigDecimal.valueOf(10));

        assertEquals(
                ReportRecord.CheckStatus.OK,
                reporter.buildReportRecord(correctSource, optTransaction).getCheckStatus()
        );

        assertEquals(
                ReportRecord.CheckStatus.ERROR,
                reporter.buildReportRecord(wrongAmountSource, optTransaction).getCheckStatus()
        );
        assertEquals(
                ReportRecord.CheckStatus.NOT_FOUND,
                reporter.buildReportRecord(wrongIdSource, Optional.empty()).getCheckStatus()
        );
    }

}
