package org.example.model;

import jakarta.persistence.*; // for Spring Boot 3

import java.time.LocalDate;

@Entity
@Table(name = "today_stock_data")
public class TodayStockData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
    private Companies companies;

    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "open", nullable = false)
    private double open;
    @Column(name = "high", nullable = false)
    private double high;
    @Column(name = "low", nullable = false)
    private double low;

    @Column(name = "close", nullable = false)
    private double close;

    @Column(name = "volume", nullable = false)
    private long volume;

    public TodayStockData( Companies companies, LocalDate date, double open, double high, double low, double close, long volume) {

        this.companies = companies;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public TodayStockData() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Companies getCompanies() {
        return companies;
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
