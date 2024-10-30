package com.DataTransferObject;

import com.Account.Entities.EmailType;

public class SendOtpDTO {
    private String email;
    private EmailType type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }
}
