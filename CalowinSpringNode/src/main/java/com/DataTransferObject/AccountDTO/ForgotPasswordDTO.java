package com.DataTransferObject.AccountDTO;

public class ForgotPasswordDTO {
    private String email;
    private String otpCode;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtCode() {
        return this.otpCode;
    }

    public void setOtCode(String otpCode) {
        this.otpCode = otpCode;
    }

}
