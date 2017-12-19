package org.jimo.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jimo on 17-9-9.
 */
public class Directory extends Entry {

    private String name;
    private List<Entry> dirs = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    String getName() {
        return name;
    }

    @Override
    int getSize() {
        int size = 0;
        Iterator<Entry> it = dirs.iterator();
        while (it.hasNext()) {
            Entry entry = it.next();
            size += entry.getSize();
        }
        return size;
    }

    @Override
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        for (Entry entry : dirs) {
            entry.printList(prefix + "/" + name);
        }
    }

    @Override
    public Entry add(Entry entry) {
        dirs.add(entry);
        return this;
    }
}
