package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.Account.Entities.FriendStatusEnum;

@Service
public class ExternalServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${friend.module.urlPrefix}")
    private String urlPrefix;

    public FriendStatusEnum getFriendStatus(String userID) {
        try {
            String url = urlPrefix + "/friend/get-friendstatus/" + userID;
            return restTemplate.getForObject(url, FriendStatusEnum.class);
        } catch (RestClientException e) {
            // Handle the error or log it
            throw new RuntimeException("Failed to retrieve friend status", e);
        }
    }

}