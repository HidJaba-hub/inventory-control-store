package com.company.electro_store.temp.storage;

import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.temp.product.Product;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Shell {
    private final StringProperty rackName;
    private final StringProperty shellId;
    private final RackDto.Place place;
    private final IntegerProperty index;
    private final DoubleProperty capacity;
    private final ObservableList<Product> product;
    public Shell(){
        this(null,null,null,0,0.0,null);
    }
    public Shell(String rackName, String shellId, RackDto.Place place, Integer index,
                  Double capacity, ObservableList<Product> product) {
        this.rackName=new SimpleStringProperty(rackName);
        this.shellId=new SimpleStringProperty(shellId);
        this.place=place;
        if(product!=null) {
            for (Product p : product) {
                capacity -= p.getWeight().get();
            }
        }
        this.capacity=new SimpleDoubleProperty(capacity);
        this.product=product;
        this.index=new SimpleIntegerProperty(index);
    }
    public void setCapacity(Double capacity){
        this.capacity.set(capacity);
    }
}
