package ru.otus.department.atm.strategy;

import ru.otus.department.atm.Cassette;
import ru.otus.department.banknote.BundleOfBanknotes;
import ru.otus.department.banknote.enums.Currency;

import java.util.List;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public interface CashOutBehavior {

    public BundleOfBanknotes getBundleToCashOut(Currency currency, int summ, List<Cassette> cassettes);

}
