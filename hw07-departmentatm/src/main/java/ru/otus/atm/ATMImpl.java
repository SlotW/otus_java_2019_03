package ru.otus.atm;

import ru.otus.banknote.Banknote;
import ru.otus.banknote.BanknoteImpl;
import ru.otus.banknote.BundleOfBanknotes;
import ru.otus.banknote.enums.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class ATMImpl implements ATM {

    private List<Cassette> cassettes = new ArrayList<>();
    private CashOutBehavior cashOutBehavior;

    public ATMImpl(){
        cashOutBehavior = new MinBanknoteBehavior();
    }

    public void setCashOutBehavior(CashOutBehavior cashOutBehavior){
        this.cashOutBehavior = cashOutBehavior;
    }

    public void loadCassette(Cassette cassette){
        cassettes.add(cassette);
    }

    @Override
    public void cashIn(Banknote banknote, int count){
        if(
                cassettes.stream()
                .filter(cassette -> cassette.getUsedBanknote().equals(banknote))
                .mapToInt(x->x.getMaxCountBanknotes() - x.getCountBanknotes())
                .sum() < count
        ) {
            System.out.println("Нужные кассеты не установлены или в них не хватит места");
        } else {
            int countForAdd = count;
            for(Cassette cassette: cassettes){
                if(cassette.getUsedBanknote().equals(banknote)){
                    countForAdd -= cassette.addBanknotesToMax(countForAdd);
                    if(countForAdd <= 0) break;
                }
            }
        }
    }

    private int getCountBanknotes(Banknote banknote){
        return cassettes.stream()
                .filter(cassette -> cassette.getUsedBanknote().equals(banknote))
                .mapToInt(Cassette::getCountBanknotes)
                .sum();
    }

    @Override
    public void cashOut(Banknote banknote, int summ){
        if(summ > getCountBanknotes(banknote) * banknote.getNominal().getValue()){
            System.out.println("Запрошенная сумма превышает наличие денег в банкомате");
        } else {
            BundleOfBanknotes bundleToCashOut = cashOutBehavior.getBundleToCashOut(banknote.getCurrency(), summ, cassettes);
            if(bundleToCashOut.getBanknotes().isEmpty()){
                System.out.println("Невозможно выдать деньги текущим набором банкнот");
            } else {
                System.out.println("К выдаче");
                bundleToCashOut.getBanknotes().forEach((k, v) -> {
                    System.out.println("Номинал:" + k.getNominal() + ", количество: " + v);
                });
                recalculateCassettes(bundleToCashOut);
                System.out.println("Выдана сумма " + summ);
            }
        }
    }

    private void recalculateCassettes(BundleOfBanknotes cashOutBundle){
        cashOutBundle.getBanknotes().forEach((banknote, count) -> {
            for(Cassette cassette: cassettes){
                if(cassette.getUsedBanknote().equals(banknote) && cassette.getCountBanknotes() > 0){
                    count -= cassette.removeBanknotesToMin(count);
                    if(count <= 0) break;
                }
            }
        });
    }

    @Override
    public int getBalance(Currency currency){
        return cassettes.stream()
                .filter(cassette -> cassette.getUsedBanknote().getCurrency() == currency)
                .mapToInt(Cassette::getBalance).sum();
    }

/*
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
*/
}
