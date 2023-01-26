package com.company.electro_store.temp.product;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private final StringProperty productName;
    private final IntegerProperty productCode;
    private final StringProperty place;
    private final DoubleProperty number;
    private final StringProperty productProperty;
    private final StringProperty provertyValue;
    private final StringProperty shellNumber;
    private final DoubleProperty price;
    private final DoubleProperty nominalPrice;
    private final DoubleProperty weight;
    private final int propertyId;

    public Product() {
        this(null,0,null,0.0,null,null,null,0.0,0.0,0,0.0);
    }
    public Product(String productName, Integer productCode, String place,
                  Double number,String productProperty, String propertyValue,
                  String shellNumber, Double price, Double nominalPrice, Integer propertyId,
                   Double weight) {
        this.productName=new SimpleStringProperty(productName);
        this.nominalPrice=new SimpleDoubleProperty(nominalPrice);
        this.place=new SimpleStringProperty(place);
        this.productCode=new SimpleIntegerProperty(productCode);
        this.productProperty=new SimpleStringProperty(productProperty);
        this.weight=new SimpleDoubleProperty(weight);
        if(shellNumber!=null)this.shellNumber=new SimpleStringProperty(shellNumber);
        else  this.shellNumber=new SimpleStringProperty("");
        this.provertyValue=new SimpleStringProperty(propertyValue);
        this.number=new SimpleDoubleProperty(number);
        this.price=new SimpleDoubleProperty(price);
        this.propertyId=propertyId;
    }
    public StringProperty productNameProperty(){
        return productName;
    }
    public IntegerProperty productCodeProperty(){
        return productCode;
    }
    public String getProductName(){return productName.get();}
    public String getPlace(){return place.get();}
    public String getProperty(){return  productProperty.get();}
    public Integer getCode(){return productCode.get();}
    public Double getPrice(){return price.get();}
    public Double getNominalPrice(){return nominalPrice.get();}
    public Double getNumber(){return number.get();}
    public String getValue(){return  provertyValue.get();}
    public String getShell(){return shellNumber.get();}

    public void setProductName(String productName){this.productName.set(productName);}
    public void setProductCode(Integer code){this.productCode.set(code);}
    public void setPlace(String place){this.place.set(place);}
    public void setProductProperty(String productProperty){this.productProperty.set(productProperty);}
    public void setNumber(Double number){this.number.set(number);}
    public void setPropertyValue(String propertyValue){this.provertyValue.set(propertyValue);}
    public void setShellNumber(String shellNumber){this.shellNumber.set(shellNumber);}
    public void setPrice(Double price){this.price.set(price);}
    public void setNominalPrice(Double nominalPrice){this.nominalPrice.set(nominalPrice);}
    public void setWeight(Double weight){this.weight.set(weight);}
}
