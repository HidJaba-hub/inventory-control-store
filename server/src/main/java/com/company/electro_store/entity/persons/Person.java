package com.company.electro_store.entity.persons;

import com.company.electro_store.entity.store.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Integer PersonId;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "phone", nullable = false)
    private String phone;

    @ToString.Exclude
    @OneToOne(cascade = { CascadeType.ALL }, mappedBy = "person", fetch = FetchType.EAGER)
    private transient User user;

    public Person(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.user = user;
    }
}
