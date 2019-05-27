package ru.otus.autologger;

/**
 * Created by Alexandr Byankin on 09.05.2019
 */
public class AutologgerMain {

    public static void main(String[] args){
        SomeActionInterface something = FabricObjects.getSomething();
        something.firstAction(3, "Три");
        something.secondAction(true);
        something.thirdAction();
    }

}
