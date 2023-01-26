package com.company.electro_store.entity.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product{
    @Id
    @Column(name = "code", nullable = false)
    private Integer code;//штрихкод

    @Column(name = "cost")
    private Double cost;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "number")
    private Double number;
    @Column(name="nominal_price")
    private Double nominalPrice;

    @Column(name="weight")
    private Double weight;
    @OneToOne(cascade = { CascadeType.MERGE } )
    @JoinColumn(name="property_id")
    private Property property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shell_id")
    private Shell shell;

    public Product(Integer code, Double nominalPrice, Property property, String name, Double number, Double weight) {
        this.name=name;
        this.code = code;
        this.nominalPrice = nominalPrice;
        this.property = property;
        this.number=number;
        this.weight=weight;
    }

}