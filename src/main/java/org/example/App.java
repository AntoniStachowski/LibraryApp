package org.example;

public class App {
    public static void main(String[] args) throws Exception {
        ServletServer servletServer = new ServletServer(4, 8080);
        servletServer.run();
    }
}
