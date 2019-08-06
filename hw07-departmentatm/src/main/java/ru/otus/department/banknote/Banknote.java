package ru.otus.department.banknote;

import ru.otus.department.banknote.enums.Currency;
import ru.otus.department.banknote.enums.Nominal;

/**
 * Created by Alexandr Byankin on 19.06.2019
 */
public interface Banknote {

    public Nominal getNominal();
    public Currency getCurrency();

}
