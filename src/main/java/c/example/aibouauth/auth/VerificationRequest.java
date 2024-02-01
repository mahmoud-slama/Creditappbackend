package c.example.aibouauth.auth;

import lombok.Data;

@Data
public class VerificationRequest {
    private String email;
    private String confirmationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getCode() {
        return  getConfirmationCode();
    }
}
