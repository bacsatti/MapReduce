package Hazi;

import java.util.Comparator;

public class TarolComparator implements Comparator<Tarolo> {
    public int compare(Tarolo t1, Tarolo t2) {
        return t1.getNev().compareTo(t2.getNev());
    }
}