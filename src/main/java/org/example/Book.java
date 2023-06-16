package org.example;

public class Book {
    final private String authorName;
    final private String bookName;

    public Book(String authorName, String bookName) {
        this.authorName = authorName;
        this.bookName = bookName;
    }

    public String toString() {
        return "Book name: " + bookName + ", Author name: " + authorName;
    }

    public String getBookName() {
        System.out.println("getBookName: " + bookName);
        return bookName;
    }

    public String getAuthorName() {

        System.out.println("getAuthorName: " + authorName);
        return authorName;
    }
}
