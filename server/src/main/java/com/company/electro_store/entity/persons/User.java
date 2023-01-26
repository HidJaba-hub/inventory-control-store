package com.company.electro_store.entity.persons;

import com.company.electro_store.entity.store.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private byte[] password;
    @Column(name = "minutes", nullable = false)
    private Double minutes;

    @OneToOne(cascade = { CascadeType.ALL } )
    @JoinColumn(name="person_id")
    private Person person;

    @OneToOne(cascade = { CascadeType.MERGE } )
    @JoinColumn(name="post_id")
    private Post post;

    public User(String login, byte[] password) {
        this.login = login;
        this.password = password;
        this.person = person;
    }
}
