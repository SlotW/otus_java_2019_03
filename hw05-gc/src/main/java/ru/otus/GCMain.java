package ru.otus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr Byankin on 19.05.2019
 */

/*
0)
-Xms512m
-Xmx512m
Thread.sleep(0)
Serial Collector = 1 m 14 s
Parallel Collector = 35 s
G1 = 40 s

1)
-Xms512m
-Xmx512m
Thread.sleep(2)
Serial Collector = 6 m 1 s
Parallel Collector = 5 m 4 s
G1 = 5 m 18 s

2)
-Xms1024m
-Xmx1024m
Thread.sleep(2)
Serial Collector = 11 m 24 s
Parallel Collector = 10 m 9 s
G1 = 10 m 54 s
 */

public class GCMain {

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        while(true){
            String str = "";
            for(int i = 0; i < 100; i ++){
                str += String.valueOf(System.currentTimeMillis());
            }
            list.add(str + "&");
            list.add(str + "|" + str);
            list.add(str + "опаопа");
            list.remove(0);
            Thread.sleep(2);
        }
    }

}
