package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockBulkDataResponseList {
    @JsonProperty("data")
    private List<StockBulkDataResponse> stockBulkDataResponses;
    @JsonProperty("status")
    private String status;

    public List<StockBulkDataResponse> getStockBulkDataResponses() {
        return stockBulkDataResponses;
    }

    public void setStockBulkDataResponses(List<StockBulkDataResponse> stockBulkDataResponses) {
        this.stockBulkDataResponses = stockBulkDataResponses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
     public static class StockBulkDataResponse{

        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("open")
        private double open;
        @JsonProperty("high")
        private double high;
        @JsonProperty("low")
        private double low;
        @JsonProperty("close")
        private double close;
        @JsonProperty("volume")
        private long volume;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public double getVolume() {
            return volume;
        }

        public void setVolume(long volume) {
            this.volume = volume;
        }
    }

}




