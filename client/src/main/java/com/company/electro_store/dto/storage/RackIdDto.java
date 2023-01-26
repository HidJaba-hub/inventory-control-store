package com.company.electro_store.dto.storage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RackIdDto implements Serializable {
    private RackDto.Place place;
    private String name;
    public RackIdDto(){}
    public RackIdDto(String name, RackDto.Place place) {
        this.name = name;
        this.place = place;
    }
}
