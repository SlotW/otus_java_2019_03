package ru.otus.banknote;

/**
 * Created by Alexandr Byankin on 19.06.2019
 */
public class SimpleEqBanknotesImpl implements EqualsBanknotes {
    @Override
    public boolean equal(Banknote banknoteOne, Banknote banknoteTwo) {
        if(banknoteOne == banknoteTwo) return true;
        if(banknoteOne == null || banknoteTwo == null) return false;
        return banknoteOne.getNominal() == banknoteTwo.getNominal()
                && banknoteOne.getCurrency() == banknoteTwo.getCurrency();
    }
}
