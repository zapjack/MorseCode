package com.kiowok.morsecode;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author agjackso
 */
public class Data {
    public static String[] alphas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static String[] morses = {
            ". -",
            "- . . .",
            "- . - .",
            "- . .",
            ".",
            ". . - .",
            "- - .",
            ". . . .",
            ". .",
            ". - - -",
            "- . -",
            ". - . .",
            "- -",
            "- .",
            "- - -",
            ". - - .",
            "- - . -",
            ". - .",
            ". . .",
            "-",
            ". . -",
            ". . . -",
            ". - -",
            "- . . -",
            "- . - -",
            "- - . ."
    };

    public static String[] level1 = {
            "A", "E", "M", "N", "T"
    };

    public static String[] level2 = {
            "A", "C", "D", "E", "M", "N", "O", "S", "T"
    };

    public static ArrayList<Data> dataAll = new ArrayList<>();

    public static ArrayList<Data> data = new ArrayList();

    public static void buildData(String[] source, ArrayList<Data> dest) {
        for (String s : source) {
            int i = indexOf(s, alphas);
            dest.add(new Data(s, morses[i]));
        }
    }

    static {
        buildData(alphas, dataAll);
    }

    private static int indexOf(String s, String[] arr) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(s))
                return i;

        return -1;
    }

    public static String normalize(String in) {
        in = in.replaceAll("\\s+", "");
        return in.toLowerCase();
    }

    public static String normalizeTranslateKeyIn(String in) {
        in = Data.normalize(in);
        in = in.replaceAll("f", ".");
        in = in.replaceAll("j", "-");
        return in;
    }

    private String alpha;
    private String morse;

    public Data(String alpha, String morse) {
        this.alpha = alpha;
        this.morse = morse;
    }

    public String getAlpha() {
        return alpha;
    }

    public String getMorse() {
        return morse;
    }

    @Override
    public String toString() {
        return alpha + " " + morse;
    }
}