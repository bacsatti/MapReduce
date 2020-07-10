package Hazi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/* A main-ben van az összes lényeges tároló amivel a program dolgozik.
Az adatok feldolgozásához van egy lista, amibe bekerül az összes adat, amit fel szeretnénk dolgozni.
Van még négy másik lista, amiben a 4 szál dolgozik.
Van egy listba amibe már a sortolt listák kerülnek és végül egy TreeMap, amibe az egész folyamat végeredménye kerül.*/
public class Main {
    static ArrayList<String> data = new ArrayList<>();
    static List<Tarolo> thread1 = Collections.synchronizedList(new ArrayList<>());
    static List<Tarolo> thread2 = Collections.synchronizedList(new ArrayList<>());
    static List<Tarolo> thread3 = Collections.synchronizedList(new ArrayList<>());
    static List<Tarolo> thread4 = Collections.synchronizedList(new ArrayList<>());
    static List<List<Tarolo>> finaly = Collections.synchronizedList(new ArrayList<>());
    static SortedMap<String, Integer> result = Collections.synchronizedSortedMap(new TreeMap<>());

    //getter metódus a tesztek miatt vannak.
    public static ArrayList<String> getData() {
        return data;
    }

    //Ebben a fv-ben fájlból olvassuk be az adatokat.
    public static void read(String f) {
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            //Addig olvassuk az adatokat, amíg nem üres sort olvasunk be. Ekkor tudjuk, hogy vége a fájlnak.
            //A for ciklus azt a cél szolgálja, hogy ne kelljen String tömböt használni, mert azzal nehezebb tovább dolgozni
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                for (String s : line.split(" ")) {
                    data.add(s);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ez a fv a beolvasott adatokat szedi szét négy listába és megnézi, hogy megfelelő formátumúak(nincs benne spec. karatker, csak 2 betús szavak stb.)
    public static void startmap() {
        // Négy szálat hozok létre, amik egymástól függetlenül fognak futni
        // A bekérés után hozom létre, mert a szálakat a konstruktor egyből el is indítja.
        //try blokban hozom létre a szálakat, mert nembiztos, hogy mindegyik el fog tudni indulni
        for (int i = 0; i < data.size(); i += 4) {
            try {
                new ThreadsForMap("One", data.get(i), thread1);
                new ThreadsForMap("Two", data.get(i + 1), thread2);
                new ThreadsForMap("Three", data.get(i + 2), thread3);
                new ThreadsForMap("Four", data.get(i + 3), thread4);
            } catch (Exception e) {}
        }
    }

    //Ez a fv a szálak által létrehozott listákat szedi szét listákra, amiket aztán egy listához ad hozzá.
    public static void startshuffle() {
        Shuffle s = new Shuffle();
        s.shuffle(thread1, finaly);
        s.shuffle(thread2, finaly);
        s.shuffle(thread3, finaly);
        s.shuffle(thread4, finaly);
    }

    //A startreduce fv-ben minden szál kap egy listát, amik szálanként egyetlen egy érték párt raknak bele a result nevű tárolóba.
    public static void startreduce() {
        while (result.size() != finaly.size()) {
            for (int i = 0; i < finaly.size(); i += 4) {
                try {
                    new ThreadsForReduce("One", finaly.get(i), result);
                    new ThreadsForReduce("Two", finaly.get(i + 1), result);
                    new ThreadsForReduce("Three", finaly.get(i + 2), result);
                    new ThreadsForReduce("Four", finaly.get(i + 3), result);
                } catch (Exception e) {
                }
            }
        }
    }

    //Egyszerűen kiiratja a végeredményül kapott adatokat a kimenetre.
    public static void kiir() {
        System.out.println(result);
    }

    //Ebben a részben, lehet választani, hogy fájlból olvasunk vagy bememenetről. Ennek függvényében folytatódnak a fv hívások.
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String answer, filename;
        System.out.println("Fájlból beolvasáshoz 'fajl', parancssoros beolvasáshoz 'kezi' parancsszó használandó:");
        answer = input.nextLine();
        while (answer != null){
            if(answer.equals("fajl") | answer.equals("kezi")){
                break;
            } else{
                System.out.println("Nem megfelelő parancs, kérlek add meg újra:");
                answer = input.nextLine();
            }
        }
        if (answer.equals("fajl")) {
            System.out.println("Add meg a fájlnevet(csak txt fájl lehet!):");
            filename = input.nextLine() + ".txt";
            read(filename);
        } else {
            if (answer.equals("kezi")) {
                System.out.println("Addig olvas be amíg nem kap egy üres sort!");
                String temp;
                temp = input.nextLine();
                while (!(temp.equals(""))) {
                    for (String s : temp.split(" ")) {
                        data.add(s);
                    }
                    temp = input.nextLine();
                }
            }
        }
        startmap();
        startshuffle();
        startreduce();
        kiir();
    }
}
