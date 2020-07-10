package Hazi;

import java.util.List;
import java.util.Map;

public class Reduce {
    public static void reduce(List<Tarolo> t, Map<String, Integer> r) {
            r.put(t.get(0).getNev(), t.size());
    }
}
