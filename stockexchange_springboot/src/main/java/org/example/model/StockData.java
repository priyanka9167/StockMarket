package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class StockData {
    private TargetInfo price_target;
    private String status;

    public TargetInfo getPrice_target() {
        return price_target;
    }

    public void setPrice_target(TargetInfo price_target) {
        this.price_target = price_target;
    }

    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "price_target=" + price_target +
                ", status='" + status + '\'' +
                '}';
    }


    public static class TargetInfo{
        private double high;
        private double median;
        private double low;
        private double average;
        private double current;

        private long volume;
        private String currency;

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

        public String getCurrency() {
            return currency;
        }

        public double getVolume() {
            return volume;
        }

        public void setVolume(long volume) {
            this.volume = volume;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @Override
        public String toString() {
            return "TargetInfo{" +
                    "high=" + high +
                    ", median=" + median +
                    ", low=" + low +
                    ", average=" + average +
                    ", current=" + current +
                    ", currency='" + currency + '\'' +
                    '}';
        }


    }
}
