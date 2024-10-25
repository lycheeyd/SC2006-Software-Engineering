package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.DataTransferObject.ViewProfileResponseDTO;
import com.Database.CalowinDB.CalowinDBRepository;

@Service
public class ProfileManagementService {

    @Autowired
    @Qualifier("calowinDBTransactionManager")
    private PlatformTransactionManager calowinDBTransactionManager;
    
    @Autowired
    private CalowinDBRepository calowinDBRepository;

    // Edit account method
    public ProfileEntity editProfile(String userID, String name, float weight, String bio) throws Exception {
        ProfileEntity profile = calowinDBRepository.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        profile.updateProfile(name, weight, bio);

        calowinDBRepository.save(profile);

        return profile;

    }

    // View account method
    public ViewProfileResponseDTO viewProfile(String userID) {
        ProfileEntity profile = calowinDBRepository.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // getMap from ziyan on friend status
        UserStatusEnum userStatus = UserStatusEnum.FRIEND; // EXAMPLE

        return new ViewProfileResponseDTO(profile.getUserID(), profile.getName(), profile.getBio(), userStatus);

    }


}
