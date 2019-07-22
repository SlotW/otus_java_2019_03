package ru.otus.writer;

public class Parameter {
    private final boolean flag;

    public Parameter(boolean flag) { this.flag = flag; }

    @Override
    public String toString() { return "{flag=" + flag + "}"; }
}
