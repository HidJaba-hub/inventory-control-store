package com.company.electro_store.dto.storage;

import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.RackId;
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
public class RackIdDto implements Serializable {
    private Rack.Place place;
    private String name;
    public RackIdDto(){}
    public RackIdDto(String name, Rack.Place place) {
        this.name = name;
        this.place = place;
    }
    public RackIdDto(RackId rackId){
        this.place=rackId.getPlace();
        this.name=rackId.getName();
    }
}
