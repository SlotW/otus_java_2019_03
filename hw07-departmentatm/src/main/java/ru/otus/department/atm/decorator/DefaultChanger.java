package ru.otus.department.atm.decorator;

/**
 * Created by Alexandr Byankin on 18.07.2019
 */
public class DefaultChanger implements ChangerSummOperation {

    private int summ;

    public DefaultChanger(int summ){
        this.summ = summ;
    }

    @Override
    public int getFinalSumm() {
        return summ;
    }
}
