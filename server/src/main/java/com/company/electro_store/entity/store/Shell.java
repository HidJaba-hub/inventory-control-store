package com.company.electro_store.entity.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shell")
public class Shell {
    @Id
    @Column(name = "shell_id", nullable = false)
    private String shell_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name="rack_name"), @JoinColumn(name="place")
    })
    private Rack rack;
    @Column(name = "place", nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Rack.Place place;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "shell_index")
    private Integer index;


    @OneToMany(mappedBy = "shell", fetch=FetchType.EAGER, orphanRemoval = false)
    private List<Product> products;

    public Shell(Integer index, Rack.Place place, Rack rack, Double capacity, String shell_id) {
        this.shell_id = shell_id;
        this.place = place;
        this.rack = rack;
        this.capacity=capacity;
        this.index=index;
    }
    public void addProduct(Product product){
        product.setShell(this);
        products.add(product);
    }
    }

