package org.example;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonblockingServletClientHandler implements Runnable {
    private Selector selector;
    private ServletServer server;


    public NonblockingServletClientHandler(ServletServer server) throws IOException {
        this.server = server;
        selector = Selector.open();
    }

    public void registerNewClient(SocketChannel channel) throws IOException {
        //Register a new client with the selector
        //System.out.println("Registering new client");
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ).attach(ByteBuffer.allocate(8192));
        selector.wakeup();
    }

    public void run() {
        //Run the server
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            var keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    try {
                        handleRead(key);
                    } catch (IOException | InterruptedException | ServletException | ClassNotFoundException |
                             InvocationTargetException | IllegalAccessException | InstantiationException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                iterator.remove();
            }
        }
    }

    private void handleRead(SelectionKey key) throws IOException, InterruptedException, ServletException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        //Handle a readable key
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer keyBuffer = (ByteBuffer) key.attachment();
        channel.read(keyBuffer);

        String request = new String(keyBuffer.array(), 0, keyBuffer.position());
        //System.out.println("Got request:" + request);

        if (request.endsWith("\n\r\n")) {
            //System.out.println("Request complete");
            parseRequest(channel, request);
            keyBuffer.clear();
        }
    }

    public void parseRequest(SocketChannel channel, String request) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ServletException {
        String[] lines = request.split("\n");
        String[] requestLine = lines[0].split(" ");
        System.out.println("Request line: " + requestLine[0]);
        String path = requestLine[1].split("\\?")[0];
        MyServletRequest myServletRequest = new MyServletRequest(request);
        System.out.println("Got request");
        MyServletResponse myServletResponse = new MyServletResponse(channel);
        System.out.println("Path: " + path);
        Servlet servlet = server.getServlet(path);
        System.out.println("Got servlet");
        servlet.init(null);
        System.out.println("Servlet initialized");
        servlet.service(myServletRequest, myServletResponse);
        System.out.println("Servlet serviced");
        servlet.destroy();
        System.out.println("Servlet destroyed");
        myServletResponse.writeToChannel();
        channel.close();
    }


}
