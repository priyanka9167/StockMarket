package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@JsonAutoDetect
public class BuyStock {

    private String stock_sy;
    private String users_id;

    private int quantity;

    private long unit_price;
    private Date date;



}
