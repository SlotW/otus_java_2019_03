package ru.otus.department.atm;

import ru.otus.department.atm.decorator.ComissionForNotRub;
import ru.otus.department.atm.decorator.ComissionForOverSumm;
import ru.otus.department.atm.decorator.DefaultChanger;
import ru.otus.department.atm.strategy.MinBanknoteBehavior;
import ru.otus.department.banknote.Banknote;
import ru.otus.department.banknote.BundleOfBanknotes;
import ru.otus.department.banknote.enums.Currency;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class ATMImpl extends AbstractATM {

    public ATMImpl(){
        cashOutBehavior = new MinBanknoteBehavior();
    }

    @Override
    public void cashOut(Banknote banknote, int summ){
        if(!isWorkAtm())return;
        System.out.println("Запросили сумму " + summ);
        int finalSumm;
        if(banknote.getCurrency().equals(Currency.RUB) || banknote.getCurrency() == Currency.OLD_RUB){
            finalSumm = new ComissionForOverSumm(new DefaultChanger(summ)).getFinalSumm();
        } else {
            finalSumm = new ComissionForNotRub(new DefaultChanger(summ)).getFinalSumm();
        }
        if(finalSumm > getCountBanknotes(banknote) * banknote.getNominal().getValue()){
            System.out.println("Запрошенная сумма превышает наличие денег в банкомате");
        } else {
            BundleOfBanknotes bundleToCashOut = cashOutBehavior.getBundleToCashOut(banknote.getCurrency(), finalSumm, cassettes);
            if(bundleToCashOut.getBanknotes().isEmpty()){
                System.out.println("Невозможно выдать деньги текущим набором банкнот");
            } else {
                saveState();
                System.out.println("К выдаче");
                bundleToCashOut.getBanknotes().forEach((k, v) -> {
                    System.out.println("Номинал:" + k.getNominal() + ", количество: " + v);
                });
                recalculateCassettes(bundleToCashOut);
                System.out.println("Выдана сумма " + finalSumm);
            }
        }
    }

}
