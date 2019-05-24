package ru.otus.autologger;

import ru.otus.autologger.annotation.Log;

/**
 * Created by Alexandr Byankin on 09.05.2019
 */
public class Something implements SomeActionInterface {

    @Log
    public void thirdAction(float some){
        System.out.println("someAction: Не логируюсь");
    }

    @Log
    @Override
    public void firstAction(int number, String str){
        System.out.println("firstAction: Логируюсь, @Log в классе");
    }

    @Override
    public void secondAction(boolean flag) {
        System.out.println("secondAction: Логируюсь, @Log в интерфейсе");
    }

    @Override
    public void thirdAction() {
        System.out.println("thirdAction: Не логируюсь, @Log отсутствует");
    }

}
