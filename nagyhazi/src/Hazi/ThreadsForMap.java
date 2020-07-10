package Hazi;

import java.util.List;

public class ThreadsForMap implements Runnable {
    List<Tarolo> storage;
    String name;
    Thread t;
    String Mydata;

    ThreadsForMap(String thread, String d, List<Tarolo> s) {
        storage = s;
        Mydata = d;
        name = thread;
        t = new Thread(this, name);
        t.start();
    }

    public void run() {
        try {
            MyMap.map(Mydata, storage);
        } catch (Exception e) {
            System.out.println(name + "InterruptedInMap");
        }
    }
}
