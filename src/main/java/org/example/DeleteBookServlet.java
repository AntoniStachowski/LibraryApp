package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name="DeleteBookServlet", urlPatterns = "/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    @Override
    public void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("DeleteBookServlet service");
        super.service(request, response);
    }

    @Override
    public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("DeleteBookServlet doGet");
        String bookName = request.getParameter("bookName");
        Library.deleteBook(bookName);
        response.sendRedirect("/");
    }
}
