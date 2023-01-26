package com.company.electro_store.entity.store;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
public class RackId implements Serializable {
    @Column(name = "place", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rack.Place place;
    @Column(name = "name", nullable = false)
    private String name;
    public RackId(){}
    public RackId(String name, Rack.Place place) {
        this.name = name;
        this.place = place;
    }
}
