package ru.otus.autologger;

import ru.otus.autologger.annotation.Log;

/**
 * Created by Alexandr Byankin on 09.05.2019
 */
public class Something implements SomeActionInterface {

    @Log
    public void someAction(){
        System.out.println("Ничего не делаю");
    }

    @Override
    public void someAction(int number, String str){
        System.out.println("Чёт делаю");
    }

    @Override
    public void secondAction(boolean flag) {
        System.out.println("Делаю всё!");
    }

}
