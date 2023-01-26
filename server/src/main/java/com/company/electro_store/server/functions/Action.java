package com.company.electro_store.server.functions;


import java.util.List;

//доступ к меню?
public enum Action {

    ADDPRODUCT( "Добавление товаров"),
    EDITPRODUCT( "Редактирование товаров"),
    DELETEPRODUCT( "Списание товаров"),
    ADDPRODUCTPLACE( "Размещение товаров на полках"),
    ADDPRICE("Назначение стоимости товару"),

    ADDWORKER( "Нанятие работников"),
    EDITWORKER( "Редактирование работников"),
    DELETEWORKER( "Увольнение работников"),
    ADDPOSTTOWORKER("Назначение должностей"),

    ADDPROPERTYTOPRODUCT("Добавить свойство продукту"),

    REVALUATION("Переоценка товаров"),
    CHEQUE("Выписка чеков"),
    CALCULATIONPRODUCT("Учет товаров"),
    ACCOUNTINGPRODUCT("Продажи товаров"),
    ACCOUNTINGPERSONS("Учет сотрудников"),
    CALCULATIONPERSONS("Расчет зарплат"),

    SHOWPRODUCTS("Меню товаров"),
    SHOWSTORAGE("Меню склада"),
    SHOWHALL("Меню зала"),
    SHOWWORKERS("Меню работников"),
    SHOWCOUNT("Меню учета");

    String str;
    Action(String str){
        this.str=str;
    }
    public static List<Action> getActions(){
        return getActions();
    }
    public String getStr() {
        return str;
    }

}
