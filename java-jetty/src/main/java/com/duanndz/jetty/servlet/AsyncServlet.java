package com.duanndz.jetty.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AsyncServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String HEAVY_RESOURCE = "This is some heavy resource that will be served in an async way";
        ByteBuffer content = ByteBuffer.wrap(HEAVY_RESOURCE.getBytes(StandardCharsets.UTF_8));

        System.out.println("Start async request");
        AsyncContext asyncContext = req.startAsync();
        ServletOutputStream out = resp.getOutputStream();

        out.setWriteListener(new WriteListener() {

            @Override
            public void onWritePossible() throws IOException {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("onWritePossible running and return response");
                while (out.isReady()) {
                    if (!content.hasRemaining()) {
                        resp.setStatus(200);
                        asyncContext.complete();
                        return;
                    }
                    out.write(content.get());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                getServletContext().log("Async Error", throwable);
                asyncContext.complete();
                System.out.println("onError running and return response");
            }
        });
        System.out.println("Completed async request");
    }
}
