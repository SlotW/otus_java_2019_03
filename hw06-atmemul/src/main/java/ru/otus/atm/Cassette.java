package ru.otus.atm;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class Cassette {

    private int nominal = 0;
    private int countNote = 0;

    private Cassette(){

    }

    public Cassette(int nominal, int countNote){
        this.nominal = nominal;
        this.countNote = countNote;
    }

    public int getNominal(){
        return nominal;
    }

    public int getCountNote() {
        return countNote;
    }

    public void addNote(int countAddNote){
        countNote += countAddNote;
    }

    public void removeNote(int countRemoveNote){
        countNote -= countRemoveNote;
    }

    public int getBalance(){
        return nominal * countNote;
    }

}
