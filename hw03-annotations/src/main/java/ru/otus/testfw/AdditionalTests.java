package ru.otus.testfw;

import ru.otus.testfw.annotations.*;

/**
 * Created by Alexandr Byankin on 28.04.2019
 */
public class AdditionalTests {

    @BeforeAll
    public static void beforeAll(){
        System.out.println("Вызов beforeALl");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("Вызов beforeEach");
        throw new RuntimeException();
    }

    @Test
    public void simpleTest(){
        System.out.println("Тест");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("Вызов afterEach");
    }

    @AfterAll
    public void afterAll(){
        System.out.println("Вызов afterAll");
    }

}
