package org.example;


import org.example.service.TwelveStockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
public class StockExchangeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(StockExchangeApplication.class,args);
    }

    @Autowired
    private TwelveStockData twelveStockData;

    @Override
    public void run(String... args) throws Exception{
        twelveStockData.sendMessage();
    }
}