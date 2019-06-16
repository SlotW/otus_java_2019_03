package ru.otus.atm;

import java.util.List;
import java.util.Map;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public interface CashOutBehavior {

    public BundleOfBanknotes getBundleToCashOut(List<Cassette> cassettes, int summ);

}
