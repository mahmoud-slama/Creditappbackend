package c.example.aibouauth.purchase;

import c.example.aibouauth.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue
    private Integer id;
    private Long total;
    private Date date;
    private Integer quantity;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public void setUser(User user) {
        this.user = user;
    }

}
