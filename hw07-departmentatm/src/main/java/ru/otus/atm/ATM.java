package ru.otus.atm;

import ru.otus.banknote.Banknote;
import ru.otus.banknote.enums.Currency;

/**
 * Created by Alexandr Byankin on 20.06.2019
 */
public interface ATM {
    public void cashIn(Banknote banknote, int count);
    public void cashOut(Banknote banknote, int summ);
    public int getBalance(Currency currency);
    //public String getInfoAboutAtms();
    //public void resetToPreviousState();
    //public void resetToStartState();
}
