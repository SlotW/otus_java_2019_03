package ru.otus.atm;

/**
 * Created by Alexandr Byankin on 13.06.2019
 */
public class Banknote {

    private int nominal;

    private Banknote() {

    }

    public Banknote(int nominal){
        this.nominal = nominal;
    }

    public int getNominal(){
        return nominal;
    }

}
