package com.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.models.UserInfo;
import com.service.UserInfoService;

@RestController
@RequestMapping("/api/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/search")
    public ResponseEntity<List<UserInfo>> searchUsers(@RequestParam String searchTerm) {
        List<UserInfo> results = userInfoService.searchByUserIdOrName(searchTerm);
        return ResponseEntity.ok(results);
    }
}

