package ru.otus.autologger;

import ru.otus.autologger.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Alexandr Byankin on 15.05.2019
 */
public class LoggerInvocationHandler implements InvocationHandler {

    private Object originalObj;

    LoggerInvocationHandler(Object originalObj){
        this.originalObj = originalObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class[] paramTypes = method.getParameterTypes();
        if(originalObj.getClass().getDeclaredMethod(method.getName(), paramTypes).isAnnotationPresent(Log.class)
                || method.isAnnotationPresent(Log.class)){
            System.out.print("executed method: " + method.getName() + ", param: ");
            for (int i = 0; i < args.length; i++){
                System.out.print(args[i]);
                if(i < method.getParameterCount()-1) System.out.print(", ");
            }
            System.out.println();
        }
        return method.invoke(originalObj, args);
    }
}
