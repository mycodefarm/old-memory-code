package org.jimo.builder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by root on 17-6-17.
 */
public class HTMLBuilder extends Builder {
    private String fileName;
    private PrintWriter writer;

    @Override
    void makeTitle(String title) {
        fileName = title + ".html";
        try {
            writer = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println("<html><head><title>" + title + "</title></head><body>");
        writer.println("<h1>" + title + "</h1>");
    }

    @Override
    void makeString(String str) {
        writer.println("<p>" + str + "</p>");
    }

    @Override
    void makeItems(String[] items) {
        writer.println("<ul>");
        for (String s : items) {
            writer.println("<li>" + s + "</li>");
        }
        writer.println("</ul>");
    }

    @Override
    void close() {
        writer.println("</body></html>");
        writer.close();
    }

    public String getResult() {
        return fileName;
    }
}
