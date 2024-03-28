package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.model.Companies;
import org.example.model.TodayStockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface TodayStockDataRepository extends JpaRepository<TodayStockData,Long> {



    @Modifying
    @Transactional
    @Query(value = "UPDATE TodayStockData td set td.date = ?1 ,td.open = ?2, td.high = ?3, td.low = ?4, td.close = ?5 where td.companies = ?6")
    void updateStockData(LocalDate date, double open, double high, double low, double close, Companies company);


    @Query(value = "SELECT t from TodayStockData t where t.companies = ?1")
    TodayStockData findByCompanies(Companies companies);
}
