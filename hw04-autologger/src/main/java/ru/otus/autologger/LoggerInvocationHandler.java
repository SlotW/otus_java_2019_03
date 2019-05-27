package ru.otus.autologger;

import ru.otus.autologger.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexandr Byankin on 15.05.2019
 */
public class LoggerInvocationHandler implements InvocationHandler {

    private Object originalObj;
    private List<Method> listMethods = new ArrayList<>();

    LoggerInvocationHandler(Object originalObj) {
        this.originalObj = originalObj;
        Class clazzObj = originalObj.getClass();
        Class<?>[] interfaces = clazzObj.getInterfaces();
        for (Method m : clazzObj.getDeclaredMethods()) {
            Arrays.stream(interfaces).anyMatch(ints ->
                    {
                        Method foundedMethodInt = null;
                        try {
                            foundedMethodInt = ints.getDeclaredMethod(m.getName(), m.getParameterTypes());
                        } catch (NoSuchMethodException e) {
                            //e.printStackTrace();
                        }
                        if (foundedMethodInt != null) {
                            if(foundedMethodInt.isAnnotationPresent(Log.class) || m.isAnnotationPresent(Log.class)) {
                                listMethods.add(foundedMethodInt);
                                return true;
                            }
                        }
                        return false;
                    }
            );
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class[] paramTypes = method.getParameterTypes();
        if (listMethods.stream().anyMatch(m -> m.equals(method))) {
            System.out.print("executed method: " + method.getName() + ", param: ");
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < method.getParameterCount() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        method.invoke(originalObj, args);
        System.out.println("======================================================");
        return null;
    }
}
