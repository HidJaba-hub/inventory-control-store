package com.company.electro_store.entity.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", nullable = false)
    private Integer propertyId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value", nullable = false)
    private String value;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "property", fetch = FetchType.EAGER, orphanRemoval = false)
    transient private Product product;//transient-не может быть сериализовано


    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
