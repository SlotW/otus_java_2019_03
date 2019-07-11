package ru.otus.banknote;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexandr Byankin on 13.06.2019
 */
public class BundleOfBanknotes {

    private final Map<Banknote, Integer> banknotes = new HashMap<>();

    public Map<Banknote, Integer> getBanknotes(){
        return banknotes;
    }

    public void addBanknotes(Banknote banknote, int count){
        if(count < 0) throw new IllegalArgumentException();
        banknotes.put(banknote, banknotes.getOrDefault(banknote, 0) + count);
    }

    public void removeBanknotes(Banknote banknote, int count) {
        if(banknotes.get(banknote) - count < 0)  throw new IllegalArgumentException();
        banknotes.put(banknote, banknotes.getOrDefault(banknote, 0) - count);
    }

    public int getCountBanknotes(Banknote banknote){
        return banknotes.getOrDefault(banknote, 0);
    }

    public int getCountBanknotes(){
        return banknotes.size();
    }
}
