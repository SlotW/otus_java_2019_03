package ru.otus.department.atm.memento;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Alexandr Byankin on 17.07.2019
 */
public class Memento implements Serializable {
    private final byte[] state;

    public Memento(byte[] state) {
        this.state = Arrays.copyOf(state, state.length);
    }

    public byte[] getState() {
        return state;
    }
}
