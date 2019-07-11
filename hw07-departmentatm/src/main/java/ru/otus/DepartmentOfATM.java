package ru.otus;

import ru.otus.atm.ATMImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr Byankin on 18.06.2019
 */
public class DepartmentOfATM {

    private List<ATMImpl> atms = new ArrayList<>();

    public void addATM(ATMImpl atm){
        if (atm != null) {
            atms.add(atm);
        }
    }

    public int getBalanceSummary(){
        return -1;
    }

    public void resetToStartState(){}

}
