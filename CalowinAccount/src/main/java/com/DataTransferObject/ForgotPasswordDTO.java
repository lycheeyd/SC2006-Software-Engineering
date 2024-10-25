package com.DataTransferObject;

public class ForgotPasswordDTO {
    private String email;
    private String otCode;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtCode() {
        return this.otCode;
    }

    public void setOtCode(String otCode) {
        this.otCode = otCode;
    }

}
