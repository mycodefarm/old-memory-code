package org.jimo.composite;

/**
 * Created by jimo on 17-9-9.
 */
public class Main {

    public static void main(String[] args) {
        Directory root = new Directory("root");
        Directory bin = new Directory("bin");
        Directory tmp = new Directory("tmp");
        Directory usr = new Directory("usr");
        root.add(bin);
        root.add(tmp);
        root.add(usr);

        bin.add(new File("vim", 1000));
        bin.add(new File("java", 10000));

        usr.add(new File("ja.txt", 1024));
        usr.add(new File("cdn.java", 100990));
        usr.add(new File("cdg.c", 10242343));

        root.printList("/");

        /**
         //root(10355357)
         //root/bin(11000)
         //root/bin/vim(1000)
         //root/bin/java(10000)
         //root/tmp(0)
         //root/usr(10344357)
         //root/usr/ja.txt(1024)
         //root/usr/cdn.java(100990)
         //root/usr/cdg.c(10242343)
         */
    }
}
