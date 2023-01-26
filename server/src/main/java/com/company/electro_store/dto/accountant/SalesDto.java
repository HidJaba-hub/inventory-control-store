package com.company.electro_store.dto.accountant;

import com.company.electro_store.dto.persons.WorkDto;
import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.Work;
import jakarta.persistence.Column;

import java.sql.Date;

public class SalesDto {
    private Integer salesId;

    private Double profit;

    private Double expences;

    private Integer amount;

    private Date date;
    public SalesDto(Double profit,Double expences,Integer amount,Date date) {
        this.profit=profit;
        this.expences=expences;
        this.amount=amount;
        this.date=date;
    }
    public SalesDto(Sales sales) {
        this.profit=sales.getProfit();
        this.expences=sales.getExpences();
        this.amount=sales.getAmount();
        this.salesId=sales.getSalesId();
        this.date=sales.getDate();
    }
}
