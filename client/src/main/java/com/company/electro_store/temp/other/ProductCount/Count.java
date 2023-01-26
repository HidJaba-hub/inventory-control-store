package com.company.electro_store.temp.other.ProductCount;

import javafx.beans.property.*;
import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@Getter
public class Count {
    private final DoubleProperty profit;
    private final IntegerProperty amount;
    private final DoubleProperty expences;
    private final ObjectProperty<LocalDate> date;
    public Count(){
        this(null,0,0.0,0.0);
    }
    public Count(Date date, Integer amount, Double profit,
                 Double expences) {
        this.amount=new SimpleIntegerProperty(amount);
        this.profit=new SimpleDoubleProperty(profit);
        Calendar calendar=GregorianCalendar.getInstance();
        calendar.setTime(date);
        this.date=new SimpleObjectProperty<LocalDate>(LocalDate.of(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));
        this.expences=new SimpleDoubleProperty(expences);
    }
}
