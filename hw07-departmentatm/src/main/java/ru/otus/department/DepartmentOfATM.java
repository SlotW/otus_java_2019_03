package ru.otus.department;

import ru.otus.department.atm.AbstractATM;
import ru.otus.department.atm.commands.Command;
import ru.otus.department.atm.commands.OffAllAtmCommand;
import ru.otus.department.atm.commands.OnAllAtmCommand;
import ru.otus.department.atm.observer.Directives;
import ru.otus.department.atm.observer.ObserverATM;
import ru.otus.department.atm.observer.ObserverATMImpl;
import ru.otus.department.banknote.enums.Currency;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexandr Byankin on 18.06.2019
 */
public class DepartmentOfATM {

    private Set<AbstractATM> atms;
    private Command offCmd;
    private Command onCmd;
    private ObserverATM observerATM;

    public DepartmentOfATM(){
        atms = new HashSet<>();
        offCmd = new OffAllAtmCommand(atms);
        onCmd = new OnAllAtmCommand(atms);
        observerATM = new ObserverATMImpl();
    }

    public void addATM(AbstractATM atm){
        atms.add(atm);
        observerATM.addListener(atm);
    }

    public String getBalanceSummary(){
        String message = "Общий баланс банкоматов\n";
        Map<Currency, Integer> mapBalances = new HashMap<>();
        for(AbstractATM atm : atms){
            Map<Currency, Integer> atmBalance = atm.getBalance();
            atmBalance.forEach((key, value) -> mapBalances.put(
                    key,
                    mapBalances.getOrDefault(key, 0) + value));
        }
        for(Map.Entry<Currency, Integer> element: mapBalances.entrySet()){
            message = message + "Валюта: " + element.getKey().getName() + " сумма: " + element.getValue() + "\n";
        }
        return message;
    }

    public void offAllAtm(){
        offCmd.execute();
    }

    public void onAllAtm(){
        onCmd.execute();
    }

    public void returnToStartState() {
        observerATM.notify(Directives.RESET);
    }
}
