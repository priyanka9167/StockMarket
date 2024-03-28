package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.model.BuyStock;
import org.example.model.Companies;
import org.example.model.Trade;
import org.example.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "SELECT t from  Trade t where  t.users = ?1 AND t.companies = ?2")
    Trade checkIfStockAndBuyerExists(Users users, Companies companies);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Trade td set td.quantity = ?3, td.unit_price = ?4, td.date = ?5 where td.companies = ?1 and td.users = ?2")
    void updateTraderData(Companies companies, Users users, int quantity, int price, Date date);
}
