package com.duanndz.http.client.uribuilder;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created By duan.nguyen at 4/13/20 1:20 AM
 */
public class UriBuilderExample {

    public static void main(String[] args) throws URISyntaxException {
        URIBuilder builder = new URIBuilder("https://qr-recv2-sb.payoo.vn/api");
        List<String> paths = builder.getPathSegments();
        paths.add("v2/get-order");
        builder.setPathSegments(paths);
        URI uri = builder.build();
        System.out.println(uri.toString());
    }

}
