package com.company.electro_store.dto.persons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonDto {
        private Integer PersonId;

        private String name;

        private String surname;

        private String phone;
        private UserDto user;


        public PersonDto(String name, String surname, String phone) {
            this.name = name;
            this.surname = surname;
            this.phone = phone;
        }
    }

