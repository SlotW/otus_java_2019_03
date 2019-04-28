package ru.otus.l021;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandr Byankin on 14.04.2019
 */
public class Runner {

    public static void main(String... args){
        List<String> exampleList = new DIYarrayList<>();
        List<String> exampleListDest = new DIYarrayList<>();

        for(int i = 0; i <25; i++){
            exampleList.add(String.valueOf(i));
            exampleListDest.add("empty");
        }

        printList(exampleListDest);

        Collections.copy(exampleListDest, exampleList);

        printList(exampleListDest);

        Collections.addAll(exampleListDest, "alien1", "zzz", "bob", "bibip", "mcnaggets", "попячься уже");

        printList(exampleListDest);

        Collections.sort(exampleListDest);

        printList(exampleListDest);
    }

    public static void printList(List listForPrint){
        listForPrint.forEach(System.out::println);
    }

}
