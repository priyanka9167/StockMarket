package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kafka.KafkaMessageProducer;
import org.example.model.Companies;
import org.example.model.StockBodyRequest;
import org.example.model.StockBulkDataResponseList;
import org.example.repository.CompaniesRepository;
import org.hibernate.query.spi.Limit;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.Random;

@Service
public class TwelveStockData {

    private KafkaTemplate<String, String> kafkaTemplate;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @Value("${stock.topic.name}")
    private String topicName;

    public TwelveStockData() {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }


    @Value("${stock.topic.name}")
    String topic;

    @Value("${data.host}")
    String stockData;

    @Autowired
    CompaniesRepository companiesRepository;


    public TwelveStockData(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


//    @Scheduled(cron = "0 0/5 * * * *")
    public void sendMessage() throws InterruptedException {
        try {
            restTemplate.setInterceptors(Arrays.asList(new RequestLoggingInterceptor()));
            StockBulkDataResponseList response = restTemplate.getForObject(stockData + "/api/stock-data", StockBulkDataResponseList.class);
            System.out.println(response);
            if (response != null) {
                System.out.println("===============Response Status:" + response.getStatus());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String json = objectMapper.writeValueAsString(response);
                    kafkaMessageProducer.sendMessage("stock-change",json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Stock Responses list is null or empty.");
                System.out.println("Full response: " + response);
            }

        } catch (Exception e) {
            System.err.println("Error" + e);
        }

    }

    private static class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException, IOException {
            System.out.println("===========================request begin================================================");
            System.out.println("URI         : " + request.getURI());
            System.out.println("Method      : " + request.getMethod());
            System.out.println("Headers     : " + request.getHeaders());
            System.out.println("Request body: " + new String(body, "UTF-8"));
            System.out.println("==========================request end================================================");

            return execution.execute(request, body);
        }
    }


}
