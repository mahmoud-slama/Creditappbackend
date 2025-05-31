package c.example.aibouauth.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    @Getter
    private String lastName;
    private String email;
    private String tel;
    private Double maxAmount;
    private BigDecimal montant = BigDecimal.ZERO;
    private Role role;



    // Convert User entity to UserDTO
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getMaxAmount(),
                user.getMontant(),
                user.getRole()
        );

    }

    // Convert a list of User entities to a list of UserDTOs
    public static List<UserDto> fromEntityList(List<User> users) {
        return users.stream().map(UserDto::fromEntity).toList();
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPhone(this.tel); // Assurez-vous que le nom du champ correspond au nom de la propriété dans la classe User
        user.setMaxAmount(this.maxAmount);
        user.setMontant(this.montant);
        user.setRole(this.role);
        // N'oubliez pas de définir d'autres propriétés de User si nécessaire
        return user;
    }
}
