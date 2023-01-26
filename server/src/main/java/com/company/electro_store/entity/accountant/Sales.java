package com.company.electro_store.entity.accountant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id", nullable = false)
    private Integer salesId;

    @Column(name = "profit", nullable = false)
    private Double profit;
    @Column(name = "expences", nullable = false)
    private Double expences;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "date", nullable = false)
    private Date date;
    public Sales(Double profit,Double expences,Integer amount,Date date) {
        this.profit=profit;
        this.expences=expences;
        this.amount=amount;
        this.date=date;
    }
}
