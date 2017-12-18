package org.jimo.templatemethod;

/**
 * Created by root on 17-6-11.
 */
public class Main {
    public static void main(String[] args) {
        AbstractDisplay h = new CharDisplay('h');
        AbstractDisplay str = new StringDisplay("hello world");
        h.display();
        str.display();
    }
}
