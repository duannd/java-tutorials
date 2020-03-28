package com.duanndz.http.client.factories;

import org.apache.http.Consts;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;

import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collections;

/**
 * duan.nguyen
 * Datetime 3/28/20 17:03
 */
public final class HttpAsyncConnectionFactory {

    private static final HttpAsyncConnectionFactory INSTANCE = new HttpAsyncConnectionFactory();

    private CloseableHttpAsyncClient ASYNC_CLIENT;

    private HttpAsyncConnectionFactory() {
        try {
            // Create I/O reactor configuration
            IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                    .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                    .setConnectTimeout(30000)
                    .setSoTimeout(30000)
                    .build();

            // Create a custom I/O reactor
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
            // Create a connection manager with custom configuration.
            PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);

            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(Integer.MAX_VALUE)
                    .build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            // Configure the connection manager to use connection configuration either
            // by default or for a specific host.
            connManager.setDefaultConnectionConfig(connectionConfig);

            // Configure total max or per route limits for persistent connections
            // that can be kept in the pool or leased by the connection manager.
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(50);

            // Use custom cookie store if necessary.
            CookieStore cookieStore = new BasicCookieStore();
            // Use custom credentials provider if necessary.
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope("localhost", 8889),
                    new UsernamePasswordCredentials("squid", "nopassword"));
            // Create global request configuration
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                    .build();

            // Create an HttpClient with the given custom dependencies and configuration.
            ASYNC_CLIENT = HttpAsyncClients.custom()
                    .setConnectionManager(connManager)
                    .setDefaultCookieStore(cookieStore)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            ASYNC_CLIENT = null;
        }
    }

}
