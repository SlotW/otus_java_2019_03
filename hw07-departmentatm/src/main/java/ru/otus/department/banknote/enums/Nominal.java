package ru.otus.department.banknote.enums;

/**
 * Created by Alexandr Byankin on 18.06.2019
 */
public enum Nominal {

    FIFTY(50),
    HUNDRED(100),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private int nominal;

    Nominal(int nominal){
        this.nominal = nominal;
    }

    public int getValue(){
        return nominal;
    }

}
