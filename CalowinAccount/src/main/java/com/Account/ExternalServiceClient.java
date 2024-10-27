package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private String friendModuleUrl;

    public ExternalServiceClient() {
    }

    @Autowired
    public ExternalServiceClient(@Value("${friend.module.url}") String friendModuleUrl) {
        this.friendModuleUrl = friendModuleUrl;
    }

    public FriendStatusEnum getFriendStatus(String userID) {
        try {
            String url = friendModuleUrl + "/friend/get-friendstatus/" + userID;
            System.out.println(url);
            return restTemplate.getForObject(url, FriendStatusEnum.class);
        } catch (RestClientException e) {
            // Handle the error or log it
            throw new RuntimeException("Failed to retrieve friend status", e);
        }
    }

}