package ru.otus.department.atm.observer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandr Byankin on 20.07.2019
 */
public class ObserverATMImpl implements ObserverATM {

    private Set<Listener> listeners;

    @Override
    public void addListener(Listener listener) {
        if (this.listeners == null){
            listeners = new HashSet<>();
        }
        listeners.add(listener);
    }

    @Override
    public void notify(Directives directive) {
        if(listeners != null){
            listeners.forEach(listener -> listener.executeDirective(directive));
        }
    }
}
