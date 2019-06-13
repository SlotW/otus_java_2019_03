package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr Byankin on 13.06.2019
 */
public class BundleOfBanknotes {

    private final List banknotes = new ArrayList();

    public List getBanknotes(){
        return banknotes;
    }

    public void addBanknote(Banknote banknote){
        banknotes.add(banknote);
    }

}
