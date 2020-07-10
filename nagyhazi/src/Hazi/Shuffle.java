package Hazi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shuffle {
    public void shuffle(List<Tarolo> data, List<List<Tarolo>> give) {
        ArrayList<Tarolo> temp = new ArrayList<>();
        Collections.sort(data, new TarolComparator());
        Boolean b;
        for (int i = 0; i < data.size(); ) {
            temp.clear();
            b = false;
            do {
                temp.add(data.get(i));
                i++;
            } while (i < data.size() && data.get(i).getNev().equals(data.get(i - 1).getNev()));
            if (give.isEmpty()) {
                give.add(new ArrayList<>());
                give.get(0).addAll(temp);
            } else {
                for (int j = 0; j < give.size(); j++) {
                    if (give.get(j).get(0).getNev().equals(temp.get(0).getNev())) {
                        for (Tarolo t : temp) {
                            give.get(j).add(t);
                        }
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    give.add(new ArrayList<>());
                    give.get(give.size() - 1).addAll(temp);
                }
            }
        }
    }
}
