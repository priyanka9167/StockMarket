package org.example.model;

import jakarta.persistence.*;

@Entity
@Table
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "symbol")
    private String symbol;

    @Column(name = "high")
    private double high;

    @Column(name = "median")
    private double median;

    @Column(name = "low")
    private double low;

    @Column(name = "average")
    private double average;

    @Column(name = "current")
    private double current;



    @Column(name = "volume")
    private double volume;

    public Data() {
    }

    public Data(long id, String symbol, double high, double median, double low, double average, double current, double volume) {
        this.id = id;
        this.symbol = symbol;
        this.high = high;
        this.median = median;
        this.low = low;
        this.average = average;
        this.current = current;
        this.volume = volume;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", high=" + high +
                ", median=" + median +
                ", low=" + low +
                ", average=" + average +
                ", current=" + current +
                ", volume=" + volume +
                '}';
    }
}
