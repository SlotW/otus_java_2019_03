package ru.otus.writer;

import com.google.gson.Gson;

import java.util.Arrays;

public class JWMain {
    public static void main(String... args) {
        Something something = new Something(
                -98,
                "Zzz",
                false,
                null,
                new Parameter[] {
                        new Parameter(false), new Parameter(true)
                },
                Arrays.asList(
                        new Parameter(true),
                        new Parameter(true)
                ),
                new long[] {1,2,3,4},
                new String[]{"курлык", "братишка"}
        );

        System.out.println(something);

        String json = JsonWriterCustom.objectToJson(something).toString();
        System.out.println("json = " + json);

        Gson gson = new Gson();
        Something deserObj = (Something)gson.fromJson(json, Something.class);
        System.out.println(deserObj);
    }
}
