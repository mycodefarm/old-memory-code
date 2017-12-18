package org.jimo.iterator;


/**
 * Created by root on 17-6-10.
 */
public class Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);

        bookShelf.appendBook(new Book("简爱"));
        bookShelf.appendBook(new Book("设计模式"));
        bookShelf.appendBook(new Book("C语言教程"));
        bookShelf.appendBook(new Book("Java进阶"));

        Iterator iterator = bookShelf.iterator();
        while (iterator.hasNext()) {
            Book book = (Book) iterator.next();
            System.out.println(book.getName());
        }
    }
}
