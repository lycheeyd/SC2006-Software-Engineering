package com.config;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;

import org.springframework.web.client.ResourceAccessException;

public class CustomRestTemplate extends RestTemplate {

    public CustomRestTemplate(RestTemplateBuilder builder) {
        super(builder.build().getRequestFactory());
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, 
                              ResponseExtractor<T> responseExtractor) throws RestClientException {

        Assert.notNull(url, "URI is required");
        Assert.notNull(method, "HttpMethod is required");
        
        ClientHttpResponse response = null;
        try {
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }

            response = request.execute();

            // Log the status and headers for debugging purposes
            //logResponseStatus(method, url, response);

            // Directly return the response without throwing exceptions for error codes
            return (responseExtractor != null ? responseExtractor.extractData(response) : null);

        } catch (IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new ResourceAccessException("I/O error on " + method.name() +
                " request for \"" + resource + "\": " + ex.getMessage(), ex);

        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
/* 
    private void logResponseStatus(HttpMethod method, URI url, ClientHttpResponse response) throws IOException {
        System.out.println("Request Method: " + method + ", URL: " + url);
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders());
    }
*/    
}

