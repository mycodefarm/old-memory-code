package org.jimo.builder;

/**
 * Created by root on 17-6-17.
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct() {
        builder.makeTitle("Greeting");
        builder.makeString("从早到晚");
        builder.makeItems(new String[]{
                "Good Morning",
                "Good Afternoon"
        });
        builder.makeString("Night");
        builder.makeItems(new String[]{
                "Good Night",
                "Night",
                "Bye"
        });
        builder.close();
    }
}
