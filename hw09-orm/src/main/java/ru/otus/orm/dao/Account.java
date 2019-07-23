package ru.otus.orm.dao;

import ru.otus.orm.annotations.Id;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class Account {

    @Id
    private final int no;
    private final String type;
    private final int rest;

    public Account(int no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public int getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public int getRest() {
        return rest;
    }

    @Override
    public String toString(){
        return "AccountRow [no=" + no + " | type=" + (type == null ? "null" : type) + " | rest=" + rest + "]";
    }

}
