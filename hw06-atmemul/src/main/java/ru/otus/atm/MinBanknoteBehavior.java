package ru.otus.atm;

import java.util.*;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class MinBanknoteBehavior implements CashOutBehavior {

    @Override
    public BundleOfBanknotes getBundleToCashOut(List<Cassette> cassettes, int summ) {
        cassettes.sort(Comparator.comparing(Cassette::getNominal));
        Collections.reverse(cassettes);
        BundleOfBanknotes resultBundle = new BundleOfBanknotes();
        for(Cassette cassette: cassettes){
            int countInCassette = cassette.getCountNote();
            while (countInCassette > 0 && cassette.getNominal() <= summ && summ > 0){
                resultBundle.addBanknote(new Banknote(cassette.getNominal()));
                summ -= cassette.getNominal();
                countInCassette--;
            }
        }
        if(summ != 0){
            resultBundle.getBanknotes().clear();
        }
        return resultBundle;
    }
}
