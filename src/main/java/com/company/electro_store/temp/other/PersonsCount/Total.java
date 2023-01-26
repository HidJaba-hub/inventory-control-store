package com.company.electro_store.temp.other.PersonsCount;

import javafx.beans.property.*;
import lombok.Getter;

@Getter
public class Total {
    private final DoubleProperty salary;
    private final StringProperty date;
    public Total(){
        this(null,0.0);
    }
    public Total(String date, Double salary) {
        this.salary=new SimpleDoubleProperty(salary);
        this.date=new SimpleStringProperty(date);
    }
}
