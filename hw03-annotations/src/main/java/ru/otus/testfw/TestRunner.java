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
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        run(AdditionalTests.class);
        run(ClassWithTests.class);
    }

    public static <T> void run(Class<T> testClazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException { //метод запуска класса с тестами
        //поиск методов с аннотацией тест (можно добавить проверку, что методов beforeAll и afterAll не больше одного)
        List<Method> testMethods = new ArrayList<>();
        List<Method> beforeAllMethods = new ArrayList<>();
        List<Method> beforeEachMethods = new ArrayList<>();
        List<Method> afterEachMethods = new ArrayList<>();
        List<Method> afterAllMethods = new ArrayList<>();

        for(Method method:testClazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(Test.class)) testMethods.add(method);
            if(method.isAnnotationPresent(BeforeAll.class)) beforeAllMethods.add(method);
            if(method.isAnnotationPresent(BeforeEach.class)) beforeEachMethods.add(method);
            if(method.isAnnotationPresent(AfterEach.class)) afterEachMethods.add(method);
            if(method.isAnnotationPresent(AfterAll.class)) afterAllMethods.add(method);
        }

        boolean hasTestClassError = false;
        if(testMethods.size() == 0 || beforeAllMethods.size() > 1 || afterAllMethods.size() > 1) hasTestClassError = true;

        System.out.println("Тестовый класс - " + testClazz.getName());
        System.out.println("Количество тестов = " + testMethods.size());
        System.out.println("beforeAll = " + beforeAllMethods.size());
        System.out.println("afterAll = " + afterAllMethods.size());
        System.out.println("beforeEach" + beforeEachMethods.size());
        System.out.println("afterAll" + afterEachMethods.size());

        if(hasTestClassError){
            System.out.println(" Класс с тестами имеет ошибки");
        } else {
            Constructor<T> constructor = testClazz.getConstructor();
            T testObject = constructor.newInstance();

            boolean hasGlobalError = false;

            if(beforeAllMethods.size() == 1){ //найти метод с аннотацией beforeAll и запустить
                try{
                    beforeAllMethods.get(0).invoke(testObject);
                } catch (Exception e){
                    hasGlobalError = true;
                    System.out.println("Сломался beforeAll");
                }
            }
            //если метод beforeAll выдал исключение не выполнять дальнейшие шаги тестов
            if(!hasGlobalError){
                for(Method testMethod:testMethods){ //для каждого метода с аннотацией Test выполнить следующие шаги
                    boolean hasTestError = false;
                    for(Method beforeMethod:beforeEachMethods){ //запустить все методы с аннотацией beforeEach
                        try{
                            beforeMethod.invoke(testObject);
                        } catch (Exception e){
                            hasTestError = true; //Если метод beforeEach выдал исключение перейти к следующему методу Test
                            System.out.println("Сломался beforeEach");
                        }
                    }

                    if(!hasTestError) {
                        try{
                            testMethod.invoke(testObject); //запустить метод с аннотацией Test
                        } catch (Exception e){
                            hasTestError = true; //если метод Test выдал исключение перейти к следующему методу Test
                            System.out.println("Сломался test");
                        }
                    }

                    if(!hasTestError){
                        for(Method afterMethod:afterEachMethods){ //найти и выполнить afterEach
                            try{
                                afterMethod.invoke(testObject);
                            } catch (Exception e){
                                System.out.println("Сломался afterEach");
                            }
                        }
                    }

                }

                if(afterAllMethods.size() == 1){ //Если Test закончились найти и выполнить AfterAll
                    try{
                        beforeAllMethods.get(0).invoke(testObject);
                    } catch (Exception e){
                        System.out.println("Сломался afterAll");
                    }
                }

            }
        }
    }

}
