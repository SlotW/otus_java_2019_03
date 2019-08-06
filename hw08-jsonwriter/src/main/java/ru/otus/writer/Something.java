package ru.otus.writer;

import java.util.Arrays;
import java.util.List;

public class Something {
    private final int number;
    private final String name;
    private final boolean isPrim;
    private final Parameter isNormal;
    private final Parameter[] arrayParameters;
    private final List<Parameter> listParameters;
    private final long[] arrLong;
    private final String[] arrString;
    private static final int CONST = 204;

    public Something(int number, String name, boolean isPrim,
                     Parameter isNormal, Parameter[] arrayParameters,
                     List<Parameter> listParameters, long[] arrLong,
                     String[] arrString) {
        this.number = number;
        this.name = name;
        this.isPrim = isPrim;
        this.isNormal = isNormal;
        this.arrayParameters = arrayParameters;
        this.listParameters = listParameters;
        this.arrLong = arrLong;
        this.arrString = arrString;
    }

    @Override
    public String toString() {
        return "from Something object info = " +
                "{number=" + number +
                ",name=" + name +
                ",isPrim=" + isPrim + "," +
                ((isNormal != null) ? ("isNormal=" + isNormal.toString() + ",") : "") +
                ("arrayParameters=" + Arrays.toString(arrayParameters) + ",") +
                ((listParameters != null && !listParameters.isEmpty()) ? ("listParameters=" + listParameters.toString()) : "") + "," +
                ("arrLong=" + Arrays.toString(arrLong) + ",") +
                ("arrString=" + Arrays.toString(arrString) + ",") +
                "CONST=" + CONST + "," +
                "}"; }

}
