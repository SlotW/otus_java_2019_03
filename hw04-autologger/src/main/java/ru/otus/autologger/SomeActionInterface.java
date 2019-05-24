package ru.otus.autologger;

import ru.otus.autologger.annotation.Log;

/**
 * Created by Alexandr Byankin on 15.05.2019
 */
public interface SomeActionInterface {

    void firstAction(int number, String str);

    @Log
    void secondAction(boolean flag);

    void thirdAction();

}
