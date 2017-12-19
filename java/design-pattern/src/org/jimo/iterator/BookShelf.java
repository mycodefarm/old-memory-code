package org.jimo.iterator;

import java.util.ArrayList;

/**
 * Created by root on 17-6-10.
 * 书架类
 */
public class BookShelf implements Aggregate {

    private ArrayList books;
    private int last = 0;

    public BookShelf(Integer size) {
        this.books = new ArrayList(size);
    }

    public void appendBook(Book book) {
        books.add(book);
        last++;
    }

    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }

    public int getLength() {
        return last;
    }

    public Object getBookAt(int index) {
        return books.get(index);
    }
}
