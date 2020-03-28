package com.duanndz.http.client.factories;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * duan.nguyen
 * Datetime 3/28/20 15:54
 */
public final class HttpClientConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConnectionFactory.class);

    private static final HttpClientConnectionFactory INSTANCE = new HttpClientConnectionFactory();
    private final CloseableHttpClient HTTP_CLIENT;

    private HttpClientConnectionFactory() {
        LOGGER.debug("Initial HttpClient Connection Pool");
        // Pooling connection manager
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200); // Increase max total connection to 200
        cm.setDefaultMaxPerRoute(50); // Increase default max connection per route to 50
//        HttpHost localhost = new HttpHost("locahost", 80);
//        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        // Connection keep alive strategy
        ConnectionKeepAliveStrategy keepAliveStrategy = (response, context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch(NumberFormatException ignore) {
                    }
                }
            }
            return 30 * 1000; // keep alive connection in 30 seconds
        };

        HTTP_CLIENT = HttpClients.custom()
                .setConnectionManager(cm)
                .setKeepAliveStrategy(keepAliveStrategy)
                .build();
    }

    public static HttpClientConnectionFactory getInstance() {
        return INSTANCE;
    }

    public void shutdown() {
        if (HTTP_CLIENT != null) {
            try {
                HTTP_CLIENT.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public CloseableHttpClient getConnection() {
        return HTTP_CLIENT;
    }

}
