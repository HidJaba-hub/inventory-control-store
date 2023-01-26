package com.company.electro_store.dto.accountant;

import com.company.electro_store.dto.persons.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalesDto {
    private Integer salesId;
    private Integer amount;

    private Double profit;
    private Double expences;
    private Date date;


    public SalesDto(Integer amount, Double profit, Double expences, Date date) {
        this.amount = amount;
        this.profit = profit;
        this.expences = expences;
        this.date = date;
    }
}
