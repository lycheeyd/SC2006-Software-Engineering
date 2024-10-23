package com.DataTransferObject.AuthDTO;

public class ChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirm_newPassword;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirm_newPassword() {
        return this.confirm_newPassword;
    }

    public void setConfirm_newPassword(String confirm_newPassword) {
        this.confirm_newPassword = confirm_newPassword;
    }

}
