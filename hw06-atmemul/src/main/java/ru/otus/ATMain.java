package ru.otus;

import ru.otus.atm.ATM;
import ru.otus.atm.Cassette;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class ATMain {

    public static void main(String[] args){
        ATM realATM = new ATM();
        realATM.loadCassette(new Cassette(5000, 10));
        realATM.loadCassette(new Cassette(100, 100));
        realATM.loadCassette(new Cassette(500, 50));
        realATM.loadCassette(new Cassette(1000, 2));
        realATM.loadCassette(new Cassette(1000, 1));
        System.out.println(realATM.getDetailBalance());

        realATM.cashIn(500, 3);
        System.out.println(realATM.getDetailBalance());
        realATM.cashOut(19500);
        System.out.println(realATM.getDetailBalance());
        realATM.cashOut(19505);
        System.out.println(realATM.getDetailBalance());

    }

}
