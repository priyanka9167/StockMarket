package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;


@Getter
@Setter

public class StockBodyRequest {

    @JsonProperty("symbols")
    private List<String> symbols;
    @JsonProperty("intervals")
    private List<String> intervals;
    @JsonProperty("outputsize")
    private int outputsize;
    @JsonProperty("methods")
    private List<String> methods;



    public StockBodyRequest(List<String> symbols, List<String> intervals, int outputsize, List<String> methods) {
        this.symbols = symbols;
        this.intervals = intervals;
        this.outputsize = outputsize;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "StockBodyRequest{" +
                "symbols=" + symbols +
                ", intervals=" + intervals +
                ", outputsize=" + outputsize +
                ", methods=" + methods +
                '}';
    }


}
