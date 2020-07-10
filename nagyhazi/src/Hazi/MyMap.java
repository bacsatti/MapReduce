package Hazi;

import java.util.List;

public class MyMap {
    static public void ciklus(String[] tar, List<Tarolo> t, int edge) {
        String s;
        for (int i = 0; i < edge; i += 2) {
            s = tar[i] + tar[i + 1];
            t.add(new Tarolo(s, 1));
        }
    }

    static public void map(String line, List<Tarolo> data) {
        String newline = line.replaceAll("[^a-zA-Z]","");
        String[] temp;
        if (newline.length() < 2) {
            return;
        } else {
            if (newline.length() == 2) {
                data.add(new Tarolo(newline, 1));
            } else {
                temp = newline.split("");
                if (newline.length() % 2 == 1) {
                    ciklus(temp, data, temp.length - 1);
                } else {
                    ciklus(temp, data, temp.length);
                }
            }
        }
    }
}
