package org.jimo.builder;

/**
 * Created by root on 17-6-17.
 */
public class TextBuilder extends Builder {
    private StringBuffer buffer = new StringBuffer();

    @Override
    void makeTitle(String title) {
        buffer.append("==============================\n");
        buffer.append("[" + title + "]\n");
        buffer.append("\n");
    }

    @Override
    void makeString(String str) {
        buffer.append("**" + str + "\n").append("\n");
    }

    @Override
    void makeItems(String[] items) {
        for (String s : items) {
            buffer.append("   -" + s + "\n");
        }
        buffer.append("\n");
    }

    @Override
    void close() {
        buffer.append("==============================\n");
    }

    public String getResult() {
        return buffer.toString();
    }
}
