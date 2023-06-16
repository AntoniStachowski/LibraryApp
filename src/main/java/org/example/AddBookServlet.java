package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name="AddBookServlet", urlPatterns = "/addBook")
public class AddBookServlet extends HttpServlet{
    @Override
    public void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("AddBookServlet service");
        super.service(request, response);
    }

    @Override
    public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("AddBookServlet doGet");
        String bookName = request.getParameter("bookName");
        String authorName = request.getParameter("authorName");
        System.out.println("AddBookServlet doGet: " + bookName + " " + authorName);
        Book book = new Book(bookName, authorName);
        Library.addBook(book);
        response.sendRedirect("/");
    }
}
