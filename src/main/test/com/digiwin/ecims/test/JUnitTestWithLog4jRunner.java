package com.digiwin.ecims.test;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

public class JUnitTestWithLog4jRunner extends SpringJUnit4ClassRunner {

    static {
        try {
            Log4jConfigurer.initLogging("classpath:log4j.properties");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public JUnitTestWithLog4jRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
