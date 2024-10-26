package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public FriendStatusEnum getFriendStatus(String userID) {
        String url = "http://localhost:8083/friend/get-friendstatus/" + userID;
        return restTemplate.getForObject(url, FriendStatusEnum.class);
    }
}