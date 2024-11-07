package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.models.UserInfo;
import com.repository.UserInfoRepository;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * Searches for users by user ID or name.
     * 
     * @param searchTerm the search term (user ID or name)
     * @return a list of UserInfo matching the search term
     */
    public List<UserInfo> searchByUserIdOrName(String searchTerm) {
        return userInfoRepository.searchByUserIdOrName(searchTerm);
    }

    /**
     * Fetches the UserInfo object by user ID.
     * 
     * @param userId the ID of the user
     * @return the UserInfo object, or null if not found
     */
    public UserInfo getUserInfoById(String userId) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
        return userInfoOptional.orElse(null);
    }

    /**
     * Fetches the user's name based on the user ID.
     * 
     * @param userId the ID of the user
     * @return the name of the user, or null if not found
     */
    public String getUserNameById(String userId) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
        return userInfoOptional.map(UserInfo::getName).orElse(null);
    }
}
