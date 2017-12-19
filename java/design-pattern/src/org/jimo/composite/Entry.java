package org.jimo.composite;

/**
 * Created by jimo on 17-9-9.
 */
public abstract class Entry {
    abstract String getName();

    abstract int getSize();

    protected abstract void printList(String prefix);

    public Entry add(Entry entry) {
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return getName() + "(" + getSize() + ")";
    }
}
