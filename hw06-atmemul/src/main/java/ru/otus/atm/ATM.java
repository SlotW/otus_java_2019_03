package ru.otus.atm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class ATM {

    private List<Cassette> cassettes = new ArrayList<Cassette>();
    private CashOutBehavior cashOutBehavior;

    public ATM(){
        cashOutBehavior = new MinBanknoteBehavior();
    }

    public void setCashOutBehavior(CashOutBehavior cashOutBehavior){
        this.cashOutBehavior = cashOutBehavior;
    }

    public void loadCassette(Cassette cassette){
        cassettes.add(cassette);
    }

    public void cashIn(int nominal, int count){
        if(cassettes.isEmpty()){
            System.out.println("Не установлены кассеты в банкомат");
        } else if(cassettes.stream().anyMatch(cas -> cas.getNominal() == nominal)){
            for (Cassette cassette : cassettes) {
                if (cassette.getNominal() == nominal) {
                    cassette.addNote(count);
                    break;
                }
            }
        }
    }

    public void cashOut(int summ){
        if(summ > getBalanceAtm()){
            System.out.println("Запрошенная сумма превышает наличие денег в банкомате на " + String.valueOf(summ - getBalanceAtm()));
        } else {
            BundleOfBanknotes bundleToCashOut = cashOutBehavior.getBundleToCashOut(cassettes, summ);
            if(bundleToCashOut.getBanknotes().isEmpty()){
                System.out.println("Невозможно выдать деньги текущим набором банкнот");
            } else {
                System.out.println("К выдаче");
                Map groupedBundle = (Map) bundleToCashOut.getBanknotes().stream()
                        .collect(Collectors.groupingBy(Banknote::getNominal, Collectors.counting()));
                ((Map) bundleToCashOut.getBanknotes().stream()
                        .collect(Collectors.groupingBy(Banknote::getNominal, Collectors.counting())))
                        .forEach((key, value) -> {
                    System.out.println("Номинал:" + key + ", количество: " + value);
                });
                recalculateCashOut(bundleToCashOut);
                System.out.println("Выдана сумма " + summ);
            }
        }
    }

    private void recalculateCashOut(BundleOfBanknotes cashOutBundle){
        for(Object banknote: cashOutBundle.getBanknotes()){
            for(Cassette cassette: cassettes){
                if(cassette.getBalance() > 0 && cassette.getNominal() == ((Banknote) banknote).getNominal()){
                    cassette.removeNote(1);
                    break;
                }
            }
        }
        /*cassettes.forEach(x->{
            if(cashOutMap.containsKey(x.getNominal()) && (Integer)cashOutMap.get(x.getNominal()) > 0){
                int dif = x.getCountNote() - (Integer) cashOutMap.get(x.getNominal());
                if(dif < 0) {
                    x.removeNote(x.getCountNote());
                    cashOutMap.replace(x.getNominal(), -dif);
                } else {
                    x.removeNote((Integer) cashOutMap.get(x.getNominal()));
                    cashOutMap.replace(x.getNominal(), 0);
                }
            }
        });*/
    }

    public int getBalanceAtm(){
        if(cassettes.isEmpty()) return 0;
        return cassettes.stream().map(Cassette::getBalance).reduce((x, y) -> x + y).orElse(0);
    }

    public String getDetailBalance(){
        String result = "";
        if(!cassettes.isEmpty()){
            for(Cassette cassette: cassettes) {
                result += "Кассета с номиналом " + cassette.getNominal()
                        + ": количество банкнот = " + cassette.getCountNote()
                        + ", остаток кассеты = " + cassette.getBalance()
                        + "\n";
            }
        }
        result += "Общий остаток " + getBalanceAtm();
        return result;
    }

}
