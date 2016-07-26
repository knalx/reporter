package com.knalx;

import com.knalx.dao.ScvDAO;
import com.knalx.model.SourceLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:test.properties")
public class ScvDAOtest {

    @Autowired
    private ScvDAO scvDAO;

    @Test
    public void TestReadCSV(){
        int count = 0;
        SourceLine sourceLine = scvDAO.readNextLine();
        while(sourceLine!=null){
            scvDAO.readNextLine();
            count++;
        }
        assertEquals(5,count);
    }

}
