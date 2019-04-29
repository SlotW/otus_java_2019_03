package ru.otus.testfw;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Alexandr Byankin on 29.04.2019
 */
public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestRunner runner = new TestRunner();
        runner.run(ClassWithTests.class);
        runner.run(AdditionalTests.class);
    }

}
