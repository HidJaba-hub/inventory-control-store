package com.company.electro_store.entity.accountant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "salaries")
public class Salaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id", nullable = false)
    private Integer salaryId;

    @Column(name = "salary", nullable = false)
    private Double salary;
    @Column(name = "date", nullable = false)
    private Date date;

    public Salaries(Double salary, Date date) {
        this.salary = salary;
        this.date = date;
    }
}
