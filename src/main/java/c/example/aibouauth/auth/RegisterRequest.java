package c.example.aibouauth.auth;

import c.example.aibouauth.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String tel;
    private Double maxAmount;
    private Double totalAmount=0.00;
    private Role role;
}
