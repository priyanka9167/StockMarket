package org.example.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import org.example.model.*;
import org.example.repository.CompaniesRepository;
import org.example.repository.TodayStockDataRepository;
import org.example.repository.TradeRepository;
import org.example.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.aot.AotServices;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MessageConsumer {

    @Value("${stock.topic.name}")
    private String stockTopic;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchsize;

    @Autowired
    TodayStockDataRepository todayStockDataRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CompaniesRepository companiesRepository;



    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void listen(String message){
        System.out.println("Receive message");
    }

    @KafkaListener(topics = "stock-buy", groupId = "stock-1")
    public void listenBuyStock(String message) throws JsonProcessingException {
        try {
            if(!message.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                BuyStock buyStock = mapper.readValue(message, BuyStock.class);
                String buyer_id = buyStock.getUsers_id();
                Users users = usersRepository.findById(buyer_id).orElse(null);
                if (users == null) {
                    System.out.println("User with ID " + buyer_id + " does not exist.");
                }
                String symbol = buyStock.getStock_sy();
                Companies companies = companiesRepository.findBySymbol(symbol);

                if (companies == null) {
                    System.out.println("Companies with symbol " + symbol + " does not exist.");
                }
                Trade oldTrade = tradeRepository.checkIfStockAndBuyerExists(users, companies);
                TodayStockData todayStockData = todayStockDataRepository.findByCompanies(companies);
                int price = (int) todayStockData.getClose();
                int quantity = buyStock.getQuantity();
                Date date = buyStock.getDate();
                if (oldTrade == null){
                    Trade trade = new Trade(companies,users,quantity,price,date);
                    tradeRepository.save(trade);
                }
                else{
                    int oldQuantity = oldTrade.getQuantity() + quantity;
                    tradeRepository.updateTraderData(companies,users,oldQuantity,price,date);
                }
            }
            else{
                System.out.println("buy stock is empty");
            }
        }
        catch (Exception e){
            System.out.println("Error" +e);
        }
    }

    @KafkaListener(topics = "stock-sell", groupId = "stock-2")
    public String listenSellStock(String message){
        try{
            if(!message.isEmpty()){
                ObjectMapper objectMapper = new ObjectMapper();
                BuyStock sellStock = objectMapper.readValue(message, BuyStock.class);
                String seller_id = sellStock.getUsers_id();
                Users users = usersRepository.findById(seller_id).orElse(null);
                if(users == null){
                    System.out.println("Users is null");
                    return "Users id null";
                }
                String symbol = sellStock.getStock_sy();
                Companies companies = companiesRepository.findBySymbol(symbol);
                if(symbol == null){
                    System.out.println("Companies doesnt exits");
                    return "Companies doesnt Exists";
                }
                Trade sellTrade = tradeRepository.checkIfStockAndBuyerExists(users, companies);
                if(sellTrade == null){
                    return "Users Doesnt have Stock";
                }
                else{
                    int sell_quantity = sellStock.getQuantity();
                    int recent_quantity = sellTrade.getQuantity();
                    int price = (int) sellTrade.getUnit_price();
                    Date date = new Date();

                    if(recent_quantity >= sell_quantity){
                        int pending_quantity = recent_quantity - sell_quantity;
                        tradeRepository.updateTraderData(companies,users,pending_quantity,price,date);
                    }
                    else{
                        return "Not enough quantity";
                    }
                }
            }
            else{
                System.out.println("Sell stock is empty");
            }
        }
        catch (Exception e){
            System.out.println("Error " +e);
        }
        return "Stock Selled Successfully";
    }

    @KafkaListener(topics = "stock-change", groupId = "group-1")
    public void listenStock(String message) throws JsonProcessingException {
        try{
            if (!message.isEmpty()){
                ObjectMapper mapper = new ObjectMapper();
                StockBulkDataResponseList stockBulkDataResponseList = mapper.readValue(message, StockBulkDataResponseList.class);
                List<StockBulkDataResponseList.StockBulkDataResponse> stockBulkDataResponses = stockBulkDataResponseList.getStockBulkDataResponses();
                List<TodayStockData> stockNewListing = new ArrayList<>();
                for(StockBulkDataResponseList.StockBulkDataResponse stock: stockBulkDataResponses){

                    String symbol = stock.getSymbol();
                    double high = stock.getHigh();
                    double open = stock.getOpen();
                    double low = stock.getLow();
                    double close = stock.getClose();
                    long vol = (long) stock.getVolume();
                    LocalDate date = LocalDate.now();

                    if (!symbol.isEmpty()){
                        Companies companies = companiesRepository.findBySymbol(symbol);
                        if (companies == null) {
                            System.out.println("Companies with symbol " + symbol + " does not exist.");
                        }
                        TodayStockData stockData = todayStockDataRepository.findByCompanies(companies);
                         if (stockData != null){
                             todayStockDataRepository.updateStockData(date,open,high,low,close,companies);
                         }
                         else{
                             if (stockNewListing.size() < batchsize){
                                 stockNewListing.add(new TodayStockData(companies,date,open,high,low,close,vol));
                             }
                             else{
                                 todayStockDataRepository.saveAll(stockNewListing);
                                 stockNewListing.clear();
                             }
                         }
                     }
                     else{
                         System.out.println("Symbol is empty");
                    }
                }
            }
        }
        catch (Exception e){
            System.err.println("Error" + e);
        }

    }


}
