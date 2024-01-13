package c.example.aibouauth.auth;


import c.example.aibouauth.user.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {


    private String accessToken;
    private String refreshToken;
    private String firstName;
    private Role role;
    private Integer id;
    private Double maxAmount;
    private Double totalAmount;
}
