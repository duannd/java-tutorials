package com.duanndz.http.client.factories;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * duan.nguyen
 * Datetime 3/28/20 16:47
 */
@RunWith(JUnit4.class)
public class HttpPostTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostTest.class);

    @Test
    public void httpPost_Success() throws IOException {
        String url = "http://localhost:8090/api/v1/status";
        HttpPost request = new HttpPost(url);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity("{}"));
        HttpResponse response = HttpClientConnectionFactory.getInstance()
                .getConnection().execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            LOGGER.info(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
    }

    @Test
    public void httpAsyncTest() throws IOException {
        String url = "http://localhost:8090/api/v1/heavy/async";
        HttpPost request = new HttpPost(url);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity("{}"));
        HttpResponse response = HttpClientConnectionFactory.getInstance()
                .getConnection().execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            LOGGER.info(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
    }

}