package com.Account.Managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.Account.ExternalServiceClient;
import com.Account.Entities.FriendStatus;
import com.Account.Entities.ProfileEntity;
import com.DataTransferObject.ViewProfileResponseDTO;
import com.Database.CalowinDB.UserInfoRepository;

@Service
public class ProfileManagementService {

    @Autowired
    @Qualifier("calowinDBTransactionManager")
    private PlatformTransactionManager calowinDBTransactionManager;
    
    @Autowired
    private UserInfoRepository calowinDBRepository;

    @Autowired
    private ExternalServiceClient externalServiceController;

    // Edit account method
    public ProfileEntity editProfile(String userID, String name, float weight, String bio) throws Exception {
        ProfileEntity profile = calowinDBRepository.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        profile.updateProfile(name, weight, bio);

        calowinDBRepository.save(profile);

        return profile;

    }

    // View account method
    public ViewProfileResponseDTO viewProfile(String selfID, String otherID) {
        ProfileEntity profile = calowinDBRepository.findByUserID(otherID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Get friend status from external service
        FriendStatus friendStatus = externalServiceController.getFriendStatus(selfID, otherID);

        return new ViewProfileResponseDTO(profile.getUserID(), profile.getName(), profile.getBio(), friendStatus);

    }

}
