package com.co2lm.botrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * @author Myouhgwhan Lee on 2017. 2. 14..
 */
@SpringBootApplication
public class RestServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestServer.class);

    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }

}
