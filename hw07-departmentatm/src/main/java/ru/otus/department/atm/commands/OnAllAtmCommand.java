package ru.otus.department.atm.commands;

import ru.otus.department.atm.AbstractATM;

import java.util.Collection;

/**
 * Created by Alexandr Byankin on 17.07.2019
 */
public class OnAllAtmCommand implements Command {

    private final Collection<AbstractATM> atms;

    public OnAllAtmCommand(Collection<AbstractATM> atms){
        this.atms = atms;
    }

    @Override
    public void execute() {
        for(AbstractATM atm : atms){
            atm.on();
        }
        System.out.println("Все банкоматы включены");
    }
}
