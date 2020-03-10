package com.duanndz.jetty;

import com.duanndz.jetty.servlet.AsyncServlet;
import com.duanndz.jetty.servlet.BlockingServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class JettyServer {

    public void start() throws Exception {
        // Jetty Configuration
        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;
        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        // Starting Jetty Server With Servlet
        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});

        // register the BlockingServlet class in the ServletHandler object
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(BlockingServlet.class, "/status");
        servletHandler.addServletWithMapping(AsyncServlet.class, "/heavy/async");

        // Add ServletHandler to server
        server.setHandler(servletHandler);

        // Start server
        server.start();
    }

}
