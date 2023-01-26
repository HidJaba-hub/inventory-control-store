package com.company.electro_store.dto.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ShellDto {
    private String shell_id;

    private RackDto rack;

    private RackDto.Place place;

    private Double capacity;

    private Integer index;
    private List<ProductDto> products=new ArrayList<>();

    public ShellDto(Integer index, RackDto.Place place, RackDto rack, Double capacity, String shell_id) {
        this.shell_id = shell_id;
        this.place = place;
        this.rack = rack;
        this.capacity=capacity;
        this.index=index;
    }
    public void addProduct(ProductDto product){
        //product.setShell(this);
        product.setShell(null);
        products.add(product);
    }
}