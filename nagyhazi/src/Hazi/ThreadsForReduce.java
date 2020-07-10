package Hazi;

import java.util.List;
import java.util.Map;

public class ThreadsForReduce implements Runnable {
    String name;
    Thread t;
    List<Tarolo> Mydata;
    Map<String, Integer> result;

    ThreadsForReduce(String thread, List<Tarolo> d, Map<String, Integer> r) {
        result = r;
        Mydata = d;
        name = thread;
        t = new Thread(this, name);
        t.start();
    }

    public void run() {
        try {
            Reduce.reduce(Mydata, result);
        } catch (Exception e) {
            System.out.println(name + "InterruptedInReduce");
        }
    }
}
