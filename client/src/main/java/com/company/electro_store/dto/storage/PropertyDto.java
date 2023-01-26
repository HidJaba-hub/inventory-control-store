package com.company.electro_store.dto.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PropertyDto {

    private Integer propertyId;
    private String name;
    private String value;

    private ProductDto product;//transient-не может быть сериализовано


    public PropertyDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

}