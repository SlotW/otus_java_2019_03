package ru.otus.atm;

import java.util.*;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class MinBanknoteBehavior implements CashOutBehavior {

    @Override
    public Map calculateBanknote(List<Cassette> cassettes, int summ) {
        Map<Integer, Integer> result = new HashMap<>();
        cassettes.sort(Comparator.comparing(Cassette::getNominal));
        Collections.reverse(cassettes);
        for(Cassette cassette: cassettes){
            if(!result.containsKey(cassette.getNominal())) result.put(cassette.getNominal(), 0);
            while (cassette.getBalance() > 0 && cassette.getNominal() <= summ && summ > 0){
                result.replace(cassette.getNominal(), result.get(cassette.getNominal()) + 1);
                summ -= cassette.getNominal();
            }
        }
        if(summ == 0){
            return result;
        } else {
            return new HashMap();
        }
    }
}
