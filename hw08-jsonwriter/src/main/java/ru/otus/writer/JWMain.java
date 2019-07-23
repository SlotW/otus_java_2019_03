package ru.otus.writer;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        String json = JsonWriterCustom.objectToJsonString(something);
        System.out.println("json = " + json);

        Gson gson = new Gson();
        Something deserObj = gson.fromJson(json, Something.class);
        System.out.println(deserObj);

        System.out.println(JsonWriterCustom.objectToJsonString(null));
        System.out.println(gson.toJson(null));

        System.out.println(JsonWriterCustom.objectToJsonString((byte)1));
        System.out.println(gson.toJson((byte)1));

        System.out.println(JsonWriterCustom.objectToJsonString((short)1f));
        System.out.println(gson.toJson((short)1f));

        System.out.println(JsonWriterCustom.objectToJsonString(1));
        System.out.println(gson.toJson(1));

        System.out.println(JsonWriterCustom.objectToJsonString(1L));
        System.out.println(gson.toJson(1L));

        System.out.println(JsonWriterCustom.objectToJsonString(1f));
        System.out.println(gson.toJson(1f));

        System.out.println(JsonWriterCustom.objectToJsonString(1d));
        System.out.println(gson.toJson(1d));

        System.out.println(JsonWriterCustom.objectToJsonString("aaa"));
        System.out.println(gson.toJson("aaa"));

        System.out.println(JsonWriterCustom.objectToJsonString('a'));
        System.out.println(gson.toJson('a'));

        System.out.println(JsonWriterCustom.objectToJsonString(new int[] {1, 2, 3}));
        System.out.println(gson.toJson(new int[] {1, 2, 3}));

        System.out.println(JsonWriterCustom.objectToJsonString(List.of(1, 2 ,3)));
        System.out.println(gson.toJson(List.of(1, 2 ,3)));

        System.out.println(JsonWriterCustom.objectToJsonString(Collections.singletonList(1)));
        System.out.println(gson.toJson(Collections.singletonList(1)));

    }
}
