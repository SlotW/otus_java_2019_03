package ru.otus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr Byankin on 03.06.2019
 */
public class Benchmark{

    public static long countAdd;

    void run() throws InterruptedException {
        List<String> list = new ArrayList<>();
        while(true){
            String str = "";
            for(int i = 0; i < 100; i ++){
                str += String.valueOf(System.currentTimeMillis());
            }
            list.add(str + "&");
            list.add(str + "|" + str);
            list.add(str + "опаопа");
            countAdd+=3;
            list.remove(0);
            Thread.sleep(2);
        }
    }

}
