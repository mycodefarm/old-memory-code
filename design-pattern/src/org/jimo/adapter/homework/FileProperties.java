package org.jimo.adapter.homework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by root on 17-6-11.
 * 适配器：从java.util.Properties类中适配出符合FileIO接口的实现
 */
public class FileProperties implements FileIO {

    private Properties properties;

    public FileProperties() {
        properties = new Properties();
    }

    @Override
    public void readFromFile(String filename) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        properties.load(fileInputStream);
    }

    @Override
    public void writeToFile(String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        properties.store(fileOutputStream, "注释");
    }

    @Override
    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
