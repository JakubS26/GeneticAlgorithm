package base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Utils {
    public static String[] splitTrim(String input, String regex) {
        return Arrays.stream(input.split(regex)).filter(s -> s.trim().length() > 0).toArray(String[]::new);
    }
    public static String findKey(File instance, String key) throws FileNotFoundException {
        String line = "";
        Scanner scan = new Scanner(instance);
        while(scan.hasNextLine() && !line.startsWith(key)) {
            line = scan.nextLine();
        }
        String[] splitLine = Utils.splitTrim(line, " ");
        scan.close();
        return splitLine[splitLine.length - 1];
    }
    public static void invert(int[] tab, int beg, int end) {
        int i = beg;
        int j = end;
        while (i < j) {
            int temp = tab[i];
            tab[i] = tab[j];
            tab[j] = temp;
            ++i;
            --j;
        }
    }
    public static void swap(int[] tab, int first, int second) {
        int temp = tab[second];
        tab[second] = tab[first];
        tab[first] = temp;
    }

    public static void insert(int[] tab, int source, int dest) {
        if (dest >= tab.length) {
            return;
        }
        if (source == dest) {
            return;
        }
        int temp = tab[source];
        if (source <= dest) {
            System.arraycopy(tab, source + 1, tab, source, dest - source);
        } else {
            System.arraycopy(tab, dest, tab, dest + 1, source - dest);
        }
        tab[dest] = temp;
    }

    public  static int findIndex(double val, double[] arr) {
        int beg = 0;
        int end = arr.length;
        while (beg < end) {
            int med = beg + (end - beg) / 2;
            if (val <= arr[med]) {
                end = med;
            } else {
                beg = med + 1;
            }
        }
        return beg;
    }
}
