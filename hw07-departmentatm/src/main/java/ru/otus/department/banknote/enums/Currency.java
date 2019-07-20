package ru.otus.department.banknote.enums;

/**
 * Created by Alexandr Byankin on 18.06.2019
 */
public enum Currency {

    RUB("Рубль"),
    USD("Доллар США"),
    OLD_RUB("Билет банка Российской империи");

    private String name;

    Currency(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
