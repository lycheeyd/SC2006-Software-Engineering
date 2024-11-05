package com.config;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;

public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(@NonNull ClientHttpResponse response) throws IOException {
        String responseBody = "";
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            responseBody = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            responseBody = "Error reading response body: " + ex.getMessage();
        }

        throw new HttpStatusCodeException(response.getStatusCode(), responseBody) {};
    }
}

