package ru.otus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexandr Byankin on 22.05.2019
 */
public class Analizer {

    public static void main(String[] args){
        printStat("./logs/SGC_1.log");
        printStat("./logs/PGC_1.log");
        printStat("./logs/G1_1.log");
        printStat("./logs/SGC_add.log");
        printStat("./logs/PGC_add.log");
        printStat("./logs/G1_add.log");
    }

    public static void printStat(String filePath){
        Statistic stat = new Statistic();
        Pattern patternForYoung = Pattern.compile(".*(Pause Young).* (\\d{1,5}\\.\\d{0,4})ms");
        Pattern patternForFull = Pattern.compile(".*(Pause Full).* (\\d{1,5}\\.\\d{0,4})ms");
        Pattern patternForOther = Pattern.compile(".*(Pause ((Remark)|(Cleanup))).* (\\d{1,5}\\.\\d{0,4})ms");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcherYoung = patternForYoung.matcher(line);
                Matcher matcherFull = patternForFull.matcher(line);
                Matcher matcherOther = patternForOther.matcher(line);
                if(matcherYoung.find()){
                    stat.youngPauses.add(Float.valueOf(matcherYoung.group(2)));
                } else
                if(matcherFull.find()){
                    stat.fullPauses.add(Float.valueOf(matcherFull.group(2)));
                } else
                if(matcherOther.find()){
                    stat.otherPauses.add(Float.valueOf(matcherOther.group(5)));
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не считался\n" + e.getMessage());
        }
        System.out.println("Файл - " + filePath);
        System.out.println("Количество пауз = " + stat.getCountPauses()
                + " || Общее время пауз GC = " + stat.getTimeAllPauses()
                + " || Среднее время пауз = " + stat.getAveragePausesTime()
        );
        System.out.println("Количество Pause Young = " + stat.youngPauses.size()
                + " || Общее время Pause Young = " + stat.getTimePausesYoung()
                + " || Среднее время Pause Young = " + stat.getAveragePausesYoungTime()
                + " || Минимальная Pause Young = " + stat.getMinPauseYoungTime()
                + " || Максимальная Pause Young = " + stat.getMaxPausesYoungTime()
        );
        System.out.println("Количество Pause Full = " + stat.fullPauses.size()
                + " || Общее время Pause Full = " + stat.getTimePausesFull()
                + " || Среднее время Pause Full = " + stat.getAveragePausesFullTime()
                + " || Минимальная Pause Full = " + stat.getMinPauseFullTime()
                + " || Максимальная Pause Full = " + stat.getMaxPausesFullTime()
        );
        System.out.println("Количество иных пауз (remark + cleanup) = " + stat.otherPauses.size()
                + " || Общее время иных пауз = " + stat.getTimePausesOther()
                + " || Среднее время иных пауз = " + stat.getAveragePausesOtherTime()
                + " || Минимальная иных пауз = " + stat.getMinPauseOtherTime()
                + " || Максимальная иных пауз = " + stat.getMaxPausesOtherTime()
        );
        System.out.println("=======================================================================");
    }

}
