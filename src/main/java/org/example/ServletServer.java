package org.example;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletServer {
    Map<String, String> urlToClassName = new HashMap<>();
    private final ServerSocketChannel serverSocketChannel;
    private final ExecutorService executorService;
    private final ArrayList<NonblockingServletClientHandler> handlers = new ArrayList<>();

    public ServletServer(int threads, int port) throws Exception {
        serverSocketChannel = ServerSocketChannel.open();
        //bind the server socket channel to a port
        serverSocketChannel.bind(new java.net.InetSocketAddress(port));
        System.out.println("Server started on port " + serverSocketChannel.socket().getLocalPort());
        executorService = Executors.newFixedThreadPool(threads);
        for(int i = 0; i < threads; i++) {
            handlers.add(new NonblockingServletClientHandler(this));
        }
    }
    public void run() throws ClassNotFoundException {
        for(NonblockingServletClientHandler handler : handlers) {
            executorService.submit(handler);
        }
        int i = 0;
        while(true) {
            try {
                //System.out.println("Waiting for new client");
                handlers.get(i).registerNewClient(serverSocketChannel.accept());
                //System.out.println("New client connected");
                i = (i + 1) % handlers.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Servlet getServlet(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if(urlToClassName.get(url) != null) {
            return (Servlet) Class.forName(urlToClassName.get(url)).getConstructor(new Class[0]).newInstance();
        }

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(WebServlet.class));

        Set<BeanDefinition> beanDefs = scanner.findCandidateComponents("org.example");
        for (BeanDefinition beanDef : beanDefs) {
            //get annotation attributes
            Map<String, Object> attributes = ((AnnotatedBeanDefinition)beanDef).getMetadata().getAnnotationAttributes(WebServlet.class.getCanonicalName());
            assert attributes != null;
            if (((String[])attributes.get("urlPatterns"))[0].equals(url)) {
                urlToClassName.put(url, beanDef.getBeanClassName());
                return (Servlet) Class.forName(beanDef.getBeanClassName()).getConstructor(new Class[0]).newInstance();
            }
        }
        System.out.println("No servlet found for url " + url);
        return null;
    }

    public int getPort() {
        return serverSocketChannel.socket().getLocalPort();
    }


}
