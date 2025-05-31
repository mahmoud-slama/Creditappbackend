package c.example.aibouauth.auth;


import c.example.aibouauth.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {


    private String accessToken;
    private String refreshToken;
    private String firstName;
    private Role role;
    private String email;
    private Integer id;
    private BigDecimal montant;
}
