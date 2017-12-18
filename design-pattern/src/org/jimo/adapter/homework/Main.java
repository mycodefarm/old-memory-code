package org.jimo.adapter.homework;

import java.io.IOException;

/**
 * Created by root on 17-6-11.
 */
public class Main {
    public static void main(String[] args) {
        FileIO f = new FileProperties();
        try {
            f.readFromFile("file.txt");
            f.setValue("year", "2020");
            f.setValue("month", "1");
            f.setValue("day", "10");
            f.writeToFile("newFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
