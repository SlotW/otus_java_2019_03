package ru.otus.l011;


import com.google.common.base.Strings;

/**
 * Created by Alexandr Byankin on 31.03.2019
 */
public class HelloOtus {

    public static void main(String[] args){
        System.out.println(Strings.padStart(">Я в конце", 50, '-'));
    }

}