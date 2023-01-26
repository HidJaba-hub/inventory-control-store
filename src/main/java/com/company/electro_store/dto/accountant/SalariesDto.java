package com.company.electro_store.dto.accountant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalariesDto {
    private Integer salaryId;

    private Double salary;
    private Date date;

    public SalariesDto(Double salary, Date date) {
        this.salary = salary;
        this.date = date;
    }
}
