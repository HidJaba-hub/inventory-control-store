package com.company.electro_store.temp.product;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class Property {
    private final StringProperty propertyName;
    private final StringProperty propertyValue;
    private final int propertyId;
    public Property(){
        this(null,null,0);
    }
    public Property(String propertyName, String propertyValue, Integer propertyId){
        this.propertyName=new SimpleStringProperty(propertyName);
        this.propertyValue=new SimpleStringProperty(propertyValue);
        this.propertyId=propertyId;
    }
    public StringProperty propertyNameProperty(){
        return propertyName;
    }
    public StringProperty propertyValueProperty(){
        return propertyValue;
    }
    public String getPropertyName(){
        return propertyName.get();
    }
    public String getPropertyValue(){
        return propertyValue.get();
    }
    public void setPropertyName(String name){
        this.propertyName.set(name);
    }
    public void setPropertyValue(String value){
        this.propertyValue.set(value);
    }
}
