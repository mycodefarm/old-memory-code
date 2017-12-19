package org.jimo.iterator;

/**
 * Created by root on 17-6-10.
 * 书架迭代器
 */
public class BookShelfIterator implements Iterator {

    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        Book book = (Book) bookShelf.getBookAt(index);
        index++;
        return book;
    }
}
