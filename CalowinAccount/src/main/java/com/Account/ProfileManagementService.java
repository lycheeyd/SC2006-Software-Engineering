package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.Database.CalowinDB.CalowinDBRepository;

@Service
public class ProfileManagementService {

    @Autowired
    @Qualifier("calowinDBTransactionManager")
    private PlatformTransactionManager calowinDBTransactionManager;
    
    @Autowired
    private CalowinDBRepository calowinDBRepository;

    // Edit account method
    //public ResponseDTO edit(String userID, String name, float weight, String bio) throws Exception {
        // UserEntity user = 

        //return new ResponseDTO(user.getUserID(), user.getEmail(), profile.getName(), profile.getWeight(), profile.getBio());

    //}


}
