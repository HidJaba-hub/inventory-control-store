package com.company.electro_store.server.functions.accountant;

import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.server.functions.Function;

import java.sql.Date;
import java.util.List;

public class SalariesFunctions extends Function {
    public static boolean saveOrUpdateSalaries(Salaries salaries){
        return salariesService.saveOrUpdate(salaries);
    }
    public static List<Salaries> getSalaries(){
        return salariesService.readAll();
    }
    public static Salaries getSalaryById(Integer id){return  salariesService.read(id);}
    public static Salaries getSalaryByDate(Date date){return salariesService.read(date);}
}
