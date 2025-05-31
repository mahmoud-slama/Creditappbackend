package c.example.aibouauth.user;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String firstName;
    @Getter
    private String lastName;
    private String email;
    private String tel;
    private Double maxAmount;
    private Double totalAmount;
    private Role role;



    // Convert User entity to UserDTO
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTel(),
                user.getMaxAmount(),
                user.getTotalAmount(),
                user.getRole()
        );

    }

    // Convert a list of User entities to a list of UserDTOs
    public static List<UserDTO> fromEntityList(List<User> users) {
        return users.stream().map(UserDTO::fromEntity).toList();
    }
}
