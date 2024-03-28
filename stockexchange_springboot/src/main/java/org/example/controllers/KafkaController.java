package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.kafka.KafkaMessageProducer;
import org.example.model.*;
import org.example.repository.CompaniesRepository;
import org.example.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class KafkaController {

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    CompaniesRepository companiesRepository;

    @PostMapping("/buy")
    public ResponseEntity<String> sendMessage(@AuthenticationPrincipal Users users, @RequestBody BuyStock buyStock) {
        try {
            if (users == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
            }
            String buyer_id = users.getId();
            buyStock.setUsers_id(buyer_id);
            buyStock.setDate(new Date());
            String symbol = buyStock.getStock_sy();
            Companies companies = companiesRepository.findBySymbol(symbol);
            if (companies == null) {
                System.out.println("Companies with symbol " + symbol + " does not exist.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Companies doesnt Exists");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(buyStock);
            kafkaMessageProducer.sendMessage("stock-buy", json);
            return ResponseEntity.status(HttpStatus.OK).body("Stock is yours");
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@AuthenticationPrincipal Users users, @RequestBody BuyStock buyStock){
            try{
                if(users == null){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
                }
                buyStock.setUsers_id(users.getId());
                buyStock.setDate(new Date());
                String symbol = buyStock.getStock_sy();
                Companies companies = companiesRepository.findBySymbol(symbol);
                if(symbol == null){
                    System.out.println("Companies doesnt exits");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Companies doesnt Exists");
                }
                Trade sellTrade = tradeRepository.checkIfStockAndBuyerExists(users, companies);
                if(sellTrade == null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users Doesnt have Stock");
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(buyStock);
                kafkaMessageProducer.sendMessage("stock-sell", json);
                return ResponseEntity.status(HttpStatus.OK).body("Stock selled");
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }









//    @GetMapping("/companies")
//    public ResponseEntity<List<Companies>> getAllCompanies(@RequestParam(required = false) String symbol){
//        try {
//            List<Companies> companies = new ArrayList<Companies>();
//
//            if(symbol == null)
//                companiesRepository.findAll().forEach(companies::add);
//            else
//                companiesRepository.findBySymbol(symbol).forEach(companies::add);
//            if(companies.isEmpty()){
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(companies, HttpStatus.OK);
//
//        } catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/getData")
    public ResponseEntity<StockData> getStockData(){
        String uri = "https://api.twelvedata.com/stocks";
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        StockData stockData = restTemplate.getForObject(uri, StockData.class);
       return ResponseEntity.ok(stockData);


    }
}
