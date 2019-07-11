package ru.otus.banknote.enums;

/**
 * Created by Alexandr Byankin on 18.06.2019
 */
public enum Currency {

    RUB("Рубль"),
    USD("Доллар США"),
    OLD_RUB("Билет банка Российской империи");

    private String currency;

    Currency(String currency){
        this.currency = currency;
    }

    public String getCurrency(){
        return currency;
    }

}
