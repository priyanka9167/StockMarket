package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "trade" ,indexes = @Index(name = "trade_item_sy_buyer_id_key", columnList = "item_sy, buyer_id"))
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "item_sy", referencedColumnName = "symbol")
    private Companies companies;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Users users;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private long unit_price;

    @Column(name = "date")
    private Date date;

    public Trade(Companies companies, Users users, int quantity, long unit_price, Date date) {
        this.companies = companies;
        this.users = users;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.date = date;
    }

    public Trade() {

    }
}
