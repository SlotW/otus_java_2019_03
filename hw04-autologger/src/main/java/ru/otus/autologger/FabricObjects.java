package ru.otus.autologger;

import java.lang.reflect.Proxy;

/**
 * Created by Alexandr Byankin on 15.05.2019
 */
public class FabricObjects {

    public static SomeActionInterface getSomething(){
        return (SomeActionInterface) Proxy.newProxyInstance
                (
                        Something.class.getClassLoader(),
                        new Class<?>[]{SomeActionInterface.class},
                        new LoggerInvocationHandler(new Something())
                );
    }

}
