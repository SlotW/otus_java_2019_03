package ru.otus;

import ru.otus.atm.ATMImpl;
import ru.otus.banknote.BanknoteImpl;
import ru.otus.banknote.BundleOfBanknotes;
import ru.otus.banknote.TicketRusEmpAdapter;
import ru.otus.banknote.TicketRussianEmpire;
import ru.otus.banknote.enums.Currency;
import ru.otus.banknote.enums.Nominal;

/**
 * Created by Alexandr Byankin on 27.05.2019
 */
public class ATMain {

    public static void main(String[] args){
        ATMImpl realATM = new ATMImpl();
        /*realATM.loadCassette(new Cassette(new BanknoteImpl(5000), 10));
        realATM.loadCassette(new Cassette(new BanknoteImpl(100), 100));
        realATM.loadCassette(new Cassette(new BanknoteImpl(500), 50));
        realATM.loadCassette(new Cassette(new BanknoteImpl(1000), 2));
        realATM.loadCassette(new Cassette(new BanknoteImpl(1000), 1));
        System.out.println(realATM.getDetailBalance());

        realATM.cashIn(500, 3);
        System.out.println(realATM.getDetailBalance());
        realATM.cashOut(19500);
        System.out.println(realATM.getDetailBalance());
        realATM.cashOut(19505);
        System.out.println(realATM.getDetailBalance());*/

        BundleOfBanknotes bundle = new BundleOfBanknotes();
        System.out.println(bundle.getCountBanknotes(new BanknoteImpl(Currency.USD, Nominal.FIFTY)));
        bundle.addBanknotes(new BanknoteImpl(Currency.USD, Nominal.FIFTY), 11);
        System.out.println(bundle.getCountBanknotes(new BanknoteImpl(Currency.USD, Nominal.FIFTY)));
        bundle.addBanknotes(new BanknoteImpl(Currency.USD, Nominal.FIFTY), 15);
        System.out.println(bundle.getCountBanknotes(new BanknoteImpl(Currency.USD, Nominal.FIFTY)));
        bundle.addBanknotes(new TicketRusEmpAdapter(new TicketRussianEmpire(100)), 5);
        System.out.println(bundle.getCountBanknotes(new TicketRusEmpAdapter(new TicketRussianEmpire(100))));

    }

}
