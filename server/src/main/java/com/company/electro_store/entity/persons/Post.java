package com.company.electro_store.entity.persons;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer post_id;

    @Column(name = "post_name", nullable = false)
    private String name;
    @Column(name = "cost", nullable = false)
    private Double cost;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Work> actions;
    @ToString.Exclude
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "post", fetch = FetchType.EAGER, orphanRemoval = false)
    private transient User user;

    public Post(String name, Double cost) {
        actions=new ArrayList<>();
        this.name = name;
        this.cost = cost;
    }
    public void addAction(Work work){
        actions.add(work);
    }
}
