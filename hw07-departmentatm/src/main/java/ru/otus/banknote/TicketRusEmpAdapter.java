package ru.otus.banknote;

import ru.otus.banknote.enums.Currency;
import ru.otus.banknote.enums.Nominal;

/**
 * Created by Alexandr Byankin on 19.06.2019
 */
public class TicketRusEmpAdapter implements Banknote {

    private TicketRussianEmpire ticket;
    private Nominal nominal;

    private TicketRusEmpAdapter(){

    }

    public TicketRusEmpAdapter(TicketRussianEmpire ticket){
        this.ticket = ticket;
        for(Nominal nom: Nominal.values()){
            if(nom.getValue() == ticket.getNominal()){
                nominal = nom;
                break;
            }
        }
        if(nominal == null){
            throw new IllegalArgumentException("Номинал банкноты российской империи не конвертируется");
        }
    }

    @Override
    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public Currency getCurrency() {
        return Currency.OLD_RUB;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()) return false;
        return new SimpleEqBanknotesImpl().equal(this, (Banknote) o);
    }

    @Override
    public int hashCode(){
        return this.getNominal().getValue() + this.getCurrency().hashCode();
    }
}
