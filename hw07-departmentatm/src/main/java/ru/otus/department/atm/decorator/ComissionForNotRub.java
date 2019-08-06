package ru.otus.department.atm.decorator;

/**
 * Created by Alexandr Byankin on 18.07.2019
 */
public class ComissionForNotRub extends ComissionChanger {

    public ComissionForNotRub(ChangerSummOperation changer) {
        super(changer);
    }

    @Override
    public int getFinalSumm() {
        System.out.println("Расчёт суммы с комиссией за выдачу в иностранной валюте");
        return changer.getFinalSumm() >= 100 ? changer.getFinalSumm()-50 : changer.getFinalSumm();
    }
}
