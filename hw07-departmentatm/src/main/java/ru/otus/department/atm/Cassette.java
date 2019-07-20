package ru.otus.department.atm;

import ru.otus.department.banknote.Banknote;
import ru.otus.department.banknote.BanknoteImpl;
import ru.otus.department.banknote.BundleOfBanknotes;

import java.io.Serializable;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class Cassette implements Serializable {

    private Banknote banknote;
    private BundleOfBanknotes bundleOfBanknotes = new BundleOfBanknotes();
    private int maxCountBanknotes = 50; //default

    private Cassette(){

    }

    public Cassette(BanknoteImpl banknoteImpl){
        this.banknote = banknoteImpl;
    }

    public Cassette(BanknoteImpl banknote, int maxCount){
        this.banknote = banknote;
        maxCountBanknotes = maxCount;
    }

    public int getMaxCountBanknotes(){
        return maxCountBanknotes;
    }

    public int getCountBanknotes() {
        return bundleOfBanknotes.getCountBanknotes(banknote);
    }

    public void addBanknotes(int countAddBanknotes){
        if(bundleOfBanknotes.getCountBanknotes() + countAddBanknotes > maxCountBanknotes) throw new RuntimeException("Кассета переполнится");
        this.bundleOfBanknotes.addBanknotes(banknote, countAddBanknotes);
    }

    public int addBanknotesToMax(int countAddBanknotes){
        int countFree = maxCountBanknotes - bundleOfBanknotes.getCountBanknotes();
        if(countFree >= countAddBanknotes){
            addBanknotes(countAddBanknotes);
            return countAddBanknotes;
        } else {
            addBanknotes(countFree);
            return countFree;
        }
    }

    public void removeBanknotes(int countRemoveBanknotes){
        if(bundleOfBanknotes.getCountBanknotes() - countRemoveBanknotes < 0) throw new RuntimeException("В кассете не хватает банкнот");
        bundleOfBanknotes.removeBanknotes(banknote, countRemoveBanknotes);
    }

    public int removeBanknotesToMin(int countRemoveBanknotes){
        if(getCountBanknotes() >= countRemoveBanknotes){
            removeBanknotes(countRemoveBanknotes);
            return countRemoveBanknotes;
        } else {
            int countRemoved = getCountBanknotes();
            removeBanknotes(countRemoved);
            return countRemoved;
        }
    }

    public int getBalance(){
        return bundleOfBanknotes.getCountBanknotes(banknote) * banknote.getNominal().getValue();
    }

    public Banknote getUsedBanknote(){
        return banknote;
    }

}
