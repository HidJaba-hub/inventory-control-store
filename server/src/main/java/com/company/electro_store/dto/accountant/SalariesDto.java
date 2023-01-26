package com.company.electro_store.dto.accountant;

import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.accountant.Sales;
import jakarta.persistence.Column;

import java.sql.Date;

public class SalariesDto {
    private Integer salaryId;
    private Double salary;
    private Date date;

    public SalariesDto(Double salary, Date date) {
        this.salary = salary;
        this.date = date;
    }
    public SalariesDto(Salaries salaries) {
        this.salary=salaries.getSalary();
        this.date=salaries.getDate();
        this.salaryId=salaries.getSalaryId();
    }
}
