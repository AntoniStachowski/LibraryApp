package org.example;

import java.util.ArrayList;

public class Library {
    public static ArrayList<Book> books = new ArrayList<Book>();

    public static void addBook(Book book) {
        books.add(book);
    }

    public static boolean deleteBook(String bookName) {
        for (Book book : books) {
            if (book.getBookName().equals(bookName)) {
                books.remove(book);
                return true;
            }
        }
        return false;
    }

    public static String booksToString() {
        StringBuilder response = new StringBuilder();
        for (Book book : books) {
            response.append(book.toString()).append("\n");
        }
        return response.toString();
    }

    public static String booksToHtml() {
        StringBuilder response = new StringBuilder();
        response.append("<html><body><table border=\"1\">");
        response.append("<tr><th>Book name</th><th>Author name</th></tr>");
        for (Book book : books) {
            response.append("<tr><td>").append(book.getBookName()).append("</td><td>").append(book.getAuthorName()).append("</td></tr>");
        }
        response.append("</table></body></html>");
        return response.toString();
    }

    public static int getBookIndex(String bookName) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookName().equals(bookName)) {
                return i;
            }
        }
        return -1;
    }

    public static Book getBook(String bookName) {
        for (Book book : books) {
            if (book.getBookName().equals(bookName)) {
                return book;
            }
        }
        return null;
    }

    public static boolean bookExists(String bookName) {
        for (Book book : books) {
            if (book.getBookName().equals(bookName)) {
                return true;
            }
        }
        return false;
    }

    public static int getBookCount() {
        return books.size();
    }
}
