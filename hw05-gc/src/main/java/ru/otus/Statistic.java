package ru.otus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr Byankin on 22.05.2019
 */
public class Statistic{
    List<Float> youngPauses = new ArrayList<>();
    List<Float> fullPauses = new ArrayList<>();
    List<Float> otherPauses = new ArrayList<>();

    int getCountPauses() {
        return youngPauses.size() + fullPauses.size() + otherPauses.size();
    }

    public Float getTimeAllPauses() {
        return getTimePausesYoung() + getTimePausesFull() + getTimePausesOther();
    }

    public Float getTimePausesYoung(){
        return youngPauses.stream().reduce(0f, (x,y) -> {return  x + y;});
    }

    public Float getTimePausesFull(){
        return fullPauses.stream().reduce(0f, (x,y) -> {return  x + y;});
    }

    public Float getTimePausesOther(){
        return otherPauses.stream().reduce(0f, (x,y) -> {return  x + y;});
    }

    public Float getAveragePausesTime() {
        return getTimeAllPauses()/getCountPauses();
    }

    public Float getAveragePausesYoungTime() {
        return getTimePausesYoung()/youngPauses.size();
    }

    public Float getMinPauseYoungTime() {
        return youngPauses.stream().min(Float::compareTo).get();
    }

    public Float getMaxPausesYoungTime() {
        return youngPauses.stream().max(Float::compareTo).get();
    }

    public Float getAveragePausesFullTime() {
        return getTimePausesFull()/fullPauses.size();
    }

    public Float getMinPauseFullTime() {
        return fullPauses.stream().min(Float::compareTo).orElse(0f);
    }

    public Float getMaxPausesFullTime() {
        return fullPauses.stream().max(Float::compareTo).orElse(0f);
    }

    public Float getAveragePausesOtherTime() {
        return getTimePausesOther()/otherPauses.size();
    }

    public Float getMinPauseOtherTime() {
        return otherPauses.stream().min(Float::compareTo).orElse(null);
    }

    public Float getMaxPausesOtherTime() {
        return otherPauses.stream().max(Float::compareTo).orElse(null);
    }
}
