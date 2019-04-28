package ru.otus.testfw;

import ru.otus.testfw.annotations.*;

/**
 * Created by Alexandr Byankin on 27.04.2019
 */
public class ClassWithTests {



    public ClassWithTests(){
        System.out.println("Вызов конструктора");
    }

    @BeforeAll
    public void beforeAllTests(){
        System.out.println("Вызов beforeAll");
    }

    @AfterAll
    public void afterAllTests(){
        System.out.println("Вызов afterAll");
    }

    @BeforeEach
    public void beforeEachFirst(){
        System.out.println("Вызов beforeEachFirst");
    }

    @BeforeEach
    public void beforeEachSecond(){
        System.out.println("Вызов beforeEachSecond");
    }

    @AfterEach
    public void afterEachFirst(){
        System.out.println("Вызов afterEachFirst");
    }

    @AfterEach
    public void afterEachSecond(){
        System.out.println("Вызов afterEachSecond");
    }

    @Test
    public void testFirst(){
        System.out.println("Тестируем раз");
        throw new RuntimeException();
    }

    @Test
    public void testSecond(){
        System.out.println("Тестируем два");
    }

}
