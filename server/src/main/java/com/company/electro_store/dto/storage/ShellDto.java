package com.company.electro_store.dto.storage;

import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import jakarta.persistence.*;
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

    private Rack.Place place;

    private Double capacity;

    private Integer index;
    private List<ProductDto> products=new ArrayList<>();

    public ShellDto(Integer index, Rack.Place place, RackDto rack, Double capacity, String shell_id) {
        this.shell_id = shell_id;
        this.place = place;
        this.rack = rack;
        this.capacity=capacity;
        this.index=index;
    }
    public void addProduct(ProductDto product){
        product.setShell(this);
        products.add(product);
    }
    public ShellDto(Shell shell, boolean clear){
        this.capacity=shell.getCapacity();
        this.index=shell.getIndex();
        this.place=shell.getPlace();
        if(clear)this.rack=null;
        else this.rack=new RackDto(shell.getRack());
        this.shell_id=shell.getShell_id();
        for(Product product: shell.getProducts()){
            this.products.add(new ProductDto(product, true));
        }
    }
    public ShellDto(Shell shell){
        this.capacity=shell.getCapacity();
        this.index=shell.getIndex();
        this.place=shell.getPlace();
        this.rack=new RackDto(shell.getRack());
        this.shell_id=shell.getShell_id();
        for(Product product: shell.getProducts()){
            this.products.add(new ProductDto(product,true));
        }
    }
}