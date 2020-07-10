package Hazi;

public class Tarolo {
    private String nev;
    private int value;

    public Tarolo(String n, int v) {
        nev = n;
        value = v;
    }

    public String getNev() {
        return nev;
    }

    public String toString() {
        return nev + " " + value;
    }
}
