package ru.otus.department.banknote;

import ru.otus.department.banknote.enums.Currency;
import ru.otus.department.banknote.enums.Nominal;

import java.io.Serializable;

/**
 * Created by Alexandr Byankin on 13.06.2019
 */
public class BanknoteImpl implements Banknote, Serializable {

    private Nominal nominal;
    private Currency currency;

    private BanknoteImpl() {

    }

    public BanknoteImpl(Currency currency, Nominal nominal){
        this.nominal = nominal;
        this.currency = currency;
    }

    @Override
    public Nominal getNominal(){
        return nominal;
    }

    @Override
    public Currency getCurrency(){
        return currency;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()) return false;
        return new SimpleEqBanknotesImpl().equal(this, (Banknote) o);
    }

    @Override
    public int hashCode(){
        return this.getNominal().getValue() + this.getCurrency().hashCode();
    }

}
