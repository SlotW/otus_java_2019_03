package ru.otus.atm;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class Cassette {

    private Banknote banknote;
    private int countBanknote = 0;

    private Cassette(){

    }

    public Cassette(Banknote banknote, int countBanknote){
        this.banknote = banknote;
        this.countBanknote = countBanknote;
    }

    public int getNominal(){
        return banknote.getNominal();
    }

    public int getCountNote() {
        return countBanknote;
    }

    public void addNote(int countAddBanknote){
        countBanknote += countAddBanknote;
    }

    public void removeNote(int countRemoveBanknote){
        countBanknote -= countRemoveBanknote;
    }

    public int getBalance(){
        return banknote.getNominal() * countBanknote;
    }

}
