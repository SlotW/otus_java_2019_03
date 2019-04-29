package ru.otus.testfw;

import ru.otus.testfw.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr Byankin on 27.04.2019
 */
public class TestRunner {

    private List<Method> testMethods = new ArrayList<>();
    private List<Method> beforeAllMethods = new ArrayList<>();
    private List<Method> beforeEachMethods = new ArrayList<>();
    private List<Method> afterEachMethods = new ArrayList<>();
    private List<Method> afterAllMethods = new ArrayList<>();

    public <T> void run(Class<T> testClazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException { //метод запуска класса с тестами
        //поиск методов с аннотацией тест (можно добавить проверку, что методов beforeAll и afterAll не больше одного)
        searchMethods(testClazz);

        boolean hasTestClassError = (testMethods.size() == 0 || beforeAllMethods.size() > 1 || afterAllMethods.size() > 1);

        System.out.println("Тестовый класс - " + testClazz.getName());
        System.out.println("Количество тестов = " + testMethods.size());
        System.out.println("beforeAll = " + beforeAllMethods.size());
        System.out.println("afterAll = " + afterAllMethods.size());
        System.out.println("beforeEach = " + beforeEachMethods.size());
        System.out.println("afterAll = " + afterEachMethods.size());

        if(hasTestClassError){
            System.out.println(" Класс с тестами имеет ошибки");
        } else {

            T beforeAfterObject = testClazz.getConstructor().newInstance();

            if(beforeAllMethods.size() == 1){ //найти метод с аннотацией beforeAll и запустить
                try{
                    beforeAllMethods.get(0).invoke(beforeAfterObject);
                    //если метод beforeAll выдал исключение не выполнять дальнейшие шаги тестов
                    for(Method testMethod:testMethods){ //для каждого метода с аннотацией Test выполнить следующие шаги

                        T testObject = testClazz.getConstructor().newInstance();

                        try {
                            for (Method beforeMethod : beforeEachMethods) { //запустить все методы с аннотацией beforeEach
                                beforeMethod.invoke(testObject);
                            }
                            try{
                                testMethod.invoke(testObject); //запустить метод с аннотацией Test
                            } catch (Exception e){
                                System.out.println("Сломался test");
                            }
                        } catch (Exception e){
                            System.out.println("Сломался beforeEach");
                        }

                        for(Method afterMethod:afterEachMethods){ //найти и выполнить afterEach
                            try{
                                afterMethod.invoke(testObject);
                            } catch (Exception e){
                                System.out.println("Сломался afterEach");
                            }
                        }

                    }
                } catch (Exception e){
                    System.out.println("Сломался beforeAll");
                }
            }

            if(afterAllMethods.size() == 1){ //Если Test закончились найти и выполнить AfterAll
                try{
                    afterAllMethods.get(0).invoke(beforeAfterObject);
                } catch (Exception e){
                    System.out.println("Сломался afterAll");
                }
            }

        }
    }

    private <T> void searchMethods(Class<T> testClazz) {
        testMethods.clear();
        beforeAllMethods.clear();
        beforeEachMethods.clear();
        afterAllMethods.clear();
        afterEachMethods.clear();
        for(Method method:testClazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(Test.class)) testMethods.add(method);
            if(method.isAnnotationPresent(BeforeAll.class)) beforeAllMethods.add(method);
            if(method.isAnnotationPresent(BeforeEach.class)) beforeEachMethods.add(method);
            if(method.isAnnotationPresent(AfterEach.class)) afterEachMethods.add(method);
            if(method.isAnnotationPresent(AfterAll.class)) afterAllMethods.add(method);
        }
    }

}
