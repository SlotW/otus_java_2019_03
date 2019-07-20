package ru.otus.department.atm.strategy;

import ru.otus.department.atm.Cassette;
import ru.otus.department.banknote.BundleOfBanknotes;
import ru.otus.department.banknote.enums.Currency;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class MinBanknoteBehavior implements CashOutBehavior, Serializable {

    @Override
    public BundleOfBanknotes getBundleToCashOut(Currency currency, int summ, List<Cassette> cassettes) {
        cassettes.sort(Comparator.comparing(cassette -> cassette.getUsedBanknote().getNominal().getValue()));
        List<Cassette> mutableCassettes = cassettes.stream()
                .filter(cassette -> cassette.getUsedBanknote().getCurrency() == currency)
                .sorted(Comparator.comparing(cassette -> cassette.getUsedBanknote().getNominal().getValue()))
                .collect(Collectors.toList());
        Collections.reverse(mutableCassettes);
        BundleOfBanknotes resultBundle = new BundleOfBanknotes();
        for(Cassette cassette: mutableCassettes){
            int countInCassette = cassette.getCountBanknotes();
            while (countInCassette > 0 && cassette.getUsedBanknote().getNominal().getValue() <= summ && summ > 0){
                resultBundle.addBanknotes(cassette.getUsedBanknote(), 1);
                summ -= cassette.getUsedBanknote().getNominal().getValue();
                countInCassette--;
            }
        }
        if(summ != 0){
            resultBundle.getBanknotes().clear();
        }
        return resultBundle;
    }
}
