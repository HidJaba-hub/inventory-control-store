package com.company.electro_store.server.functions.accountant;

import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.server.functions.Function;

import java.sql.Date;
import java.util.List;

public class SalesFunctions extends Function {
    public static boolean saveOrUpdateSales(Sales Sales){
        return salesService.saveOrUpdate(Sales);
    }
    public static List<Sales> getSales(){
        return salesService.readAll();
    }
    public static Sales getSalaryById(Integer id){return  salesService.read(id);}
    public static Sales getSalaryByDate(Date date){return salesService.read(date);}
}
