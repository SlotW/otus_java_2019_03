package ru.otus.department.atm.decorator;

/**
 * Created by Alexandr Byankin on 18.07.2019
 */
public abstract class ComissionChanger implements ChangerSummOperation {

    protected ChangerSummOperation changer;

    public ComissionChanger(ChangerSummOperation changer){
        this.changer = changer;
    }

}
