package org.jimo.adapter.homework;

import java.io.IOException;

/**
 * Created by root on 17-6-11.
 */
public interface FileIO {
    void readFromFile(String filename) throws IOException;

    void writeToFile(String filename) throws IOException;

    void setValue(String key, String value);

    String getValue(String key);
}
