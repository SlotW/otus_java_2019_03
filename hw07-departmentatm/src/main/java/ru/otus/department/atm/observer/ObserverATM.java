package ru.otus.department.atm.observer;

/**
 * Created by Alexandr Byankin on 17.07.2019
 */
public interface ObserverATM {

    public void addListener(Listener listener);
    public void notify(Directives directive);

}
