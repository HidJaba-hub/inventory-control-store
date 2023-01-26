package com.company.electro_store.dto.storage;

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
    private Double weight;
    private String name;
    private PropertyDto property;
    private ShellDto shell;
    private Double nominalPrice;
    private Double number;

    public ProductDto(Integer code, Double nominalPrice, PropertyDto property, String name, Double number, Double weight) {
        this.name=name;
        this.code = code;
        this.nominalPrice = nominalPrice;
        this.property = property;
        this.number=number;
        this.weight=weight;
    }

}