package com.company.electro_store.temp.other.PersonsCount;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Today {
    private final DoubleProperty total;
    private final DoubleProperty hours;
    private final DoubleProperty salary;
    private final StringProperty name;
    private final StringProperty surname;

    public Today(){
        this(null,null,0.0,0.0,0.0);
    }
    public Today(String name,String surname, Double salary, Double total, Double hours) {
        this.salary=new SimpleDoubleProperty(salary);
        this.total=new SimpleDoubleProperty(total);
        this.hours=new SimpleDoubleProperty(hours);
        this.name=new SimpleStringProperty(name);
        this.surname=new SimpleStringProperty(surname);
    }
    public void setTotal(Double total){this.total.set(total);}
    public void setHours(Double hours){this.hours.set(hours);}
    public void setSalary(Double salary){this.salary.set(salary);}
    public void setName(String name){this.name.set(name);}
    public void setSurname(String surname){this.surname.set(surname);}
}
