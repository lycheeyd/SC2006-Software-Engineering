package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.models.UserInfo;
import com.repository.UserInfoRepository;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<UserInfo> searchByUserIdOrName(String searchTerm) {
        return userInfoRepository.searchByUserIdOrName(searchTerm);
    }
}

