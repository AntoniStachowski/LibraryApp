package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(name="RootServlet", urlPatterns = "/")
public class RootServlet extends HttpServlet {

    static final String htmlRoot = """
                HTTP/1.1 200 OK
                Content-Type: text/html
                
                \n
                <html><head><link rel="icon" href="data:,"></head><body><form action="http://localhost:8080/addBook" method="get">
                
                  Book name:<br>
                
                  <input type="text" name="bookName" value="">
                
                  <br>
                
                  Author name:<br>
                
                  <input type="text" name="authorName" value="">
                
                  <br><br>
                
                  <input type="submit" value="Submit">
                
                </form>
                
                <form action="http://localhost:8080/deleteBook" method="get">
                
                  Book name:<br>
                
                  <input type="text" name="bookName" value="">
                
                  <br><br>
                
                  <input type="submit" value="Submit">
                
                </form> \n
                <form action="http://localhost:8080/showBooks" method="get">
                
                  <input type="submit" value="View all books">
                
                </form> </body></html>
                
                \n
                """;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("RootServlet service");
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("RootServlet doGet");
        PrintWriter writer = response.getWriter();
        writer.write(htmlRoot);
        writer.flush();
    }
}
