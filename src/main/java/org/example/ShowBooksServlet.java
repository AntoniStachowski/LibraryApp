package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;

@WebServlet(name="ShowBooksServlet", urlPatterns = "/showBooks")
public class ShowBooksServlet extends HttpServlet {

    @Override
    public void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("ShowBooksServlet service");
        super.service(request, response);
    }

    @Override
    public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("ShowBooksServlet doGet");
        String html = """
                HTTP/1.1 200 OK
                Content-Type: text/html
                
                \n
                <html><head><link rel="icon" href="data:,"></head><body>
                """;
        System.out.println("ShowBooksServlet doGet");
        html += Library.booksToHtml();
        System.out.println("ShowBooksServlet doGet");
        html += """
                </body></html>
                
                \n
                """;
        PrintWriter out = response.getWriter();
        out.println(html);
        out.flush();
    }
}
