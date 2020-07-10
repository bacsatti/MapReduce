package Test;

import Hazi.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

public class MainTest {

    Main m;
    MyMap map;
    Shuffle shuf;
    Reduce red;

    //testread
    private List<String> test = new ArrayList<>();
    private String line;

    //testgetNev
    private String nev = "ap";

    //testtoString
    private Tarolo t1;

    //testCompare
    private List<Tarolo> comp = new ArrayList<>();
    private Tarolo t2;

    //testciklus
    private List<Tarolo> refer = new ArrayList<>();
    private String[] tar;

    //testShuffle
    private List<List<Tarolo>> veg =new ArrayList<>();
    private List<Tarolo> refer2 = new ArrayList<>();
    private List<Tarolo> comp2 = new ArrayList<>();
    private Tarolo t3;

    //testreduce
    private Map<String, Integer> r = new TreeMap<>();
    private Tarolo t4;
    private Tarolo t5;

    @Before
    public void setup() {
        line = "al ap oe ke al pe mr tk al ap oe oe al pe mr tk alma kalap [] al{ma";
        for (String s : line.split(" ")) {
            test.add(s);
        }
        t1 = new Tarolo("ap", 1);
        t2 = new Tarolo("ws", 1);
        comp.add(t2);
        comp.add(t1);
        tar = new String[]{"k", "a", "l", "a", "p"};
        refer.add(new Tarolo("ka", 1));
        refer.add(new Tarolo("la", 1));
        refer2.add(new Tarolo("ka", 1));
        refer2.add(new Tarolo("ka", 1));
        t3 = new Tarolo("la", 1);
        comp2.add(new Tarolo("ap", 1));
        comp2.add(new Tarolo("la", 1));
        comp2.add(new Tarolo("ap", 1));
        t4 = new Tarolo("ap",2);
        t5 = new Tarolo("la", 1);
    }

    @Test
    public void testread() {
        m.read("test.txt");
        assertEquals(test, m.getData());
    }

    @Test
    public void testgetNev() {
        assertEquals(nev, t1.getNev());
    }

    @Test
    public void testtoString() {
        assertEquals("ap 1", t1.toString());
    }

    @Test
    public void testCompare() {
        Collections.sort(comp, new TarolComparator());
        assertEquals(t1, comp.get(0));
        assertEquals(t2, comp.get(1));
    }

    @Test
    public void testciklus() {
        comp.clear();
        map.ciklus(tar, comp, 4);
        assertEquals(refer.get(0).getNev(), comp.get(0).getNev());
        assertEquals(refer.get(1).getNev(), comp.get(1).getNev());

    }

    @Test
    public void testMyThread() {
        comp.clear();
        map.map("kalap", comp);
        assertEquals(refer.get(0).getNev(), comp.get(0).getNev());
        assertEquals(refer.get(1).getNev(), comp.get(1).getNev());
    }

    @Test
    public void testShuffle() {
        shuf.shuffle(comp2, veg);
        assertEquals(refer2, veg.get(0));
        assertEquals(t3, veg.get(1));
    }

    @Test
    public void testReduce(){
        red.reduce(veg.get(0),r);
        red.reduce(veg.get(1),r);
        assertEquals(t4,r.get(0));
        assertEquals(t5,r.get(1));
    }
}
