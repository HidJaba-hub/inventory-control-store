package com.company.electro_store.dto.persons;

import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.security.PublicKey;

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
        public PersonDto(Person person){
            this.name=person.getName();
            this.PersonId=person.getPersonId();
            this.surname=person.getSurname();
            this.phone=person.getPhone();
            //this.user=person.getUser();
            if(person.getUser()!=null)this.user=new UserDto(person.getUser(), true);

        }
    }

