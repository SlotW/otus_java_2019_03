package ru.otus.atm;

import ru.otus.banknote.BundleOfBanknotes;
import ru.otus.banknote.enums.Currency;

import java.util.List;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public interface CashOutBehavior {

    public BundleOfBanknotes getBundleToCashOut(Currency currency, int summ, List<Cassette> cassettes);

}
