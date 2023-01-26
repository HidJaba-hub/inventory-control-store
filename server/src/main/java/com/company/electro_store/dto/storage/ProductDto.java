package com.company.electro_store.dto.storage;

import com.company.electro_store.dto.persons.PersonDto;
import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.entity.store.Shell;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto{

    private Integer code;//штрихкод
    private Double cost;
    private String name;
    private PropertyDto property;
    private ShellDto shell;
    private Double nominalPrice;
    private Double number;
    private Double weight;

    public ProductDto(Integer code, Double cost, PropertyDto property, String name) {
        this.name=name;
        this.code = code;
        this.cost = cost;
        this.property = property;
    }
    public ProductDto(Product product){
        this.code=product.getCode();
        this.cost=product.getCost();
        this.name=product.getName();
        this.nominalPrice=product.getNominalPrice();
        this.number=product.getNumber();
        this.weight=product.getWeight();
        if(product.getProperty()!=null)this.property=new PropertyDto(product.getProperty());
        if(product.getShell()!=null)this.shell=new ShellDto(product.getShell());
    }
    public ProductDto(Product product, boolean clear){
        this.code=product.getCode();
        this.cost=product.getCost();
        this.name=product.getName();
        this.weight=product.getWeight();
        this.nominalPrice=product.getNominalPrice();
        this.number=product.getNumber();
        if(product.getProperty()!=null)this.property=new PropertyDto(product.getProperty());
        if(clear)this.shell=null;
    }

}