package com.currencyfair;

/**
 * Created by davidb on 3/23/15.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class MarketTrade {
    public static void main(String[] args) {
        SpringApplication.run(com.currencyfair.MarketTrade.class, args);
    }
}
