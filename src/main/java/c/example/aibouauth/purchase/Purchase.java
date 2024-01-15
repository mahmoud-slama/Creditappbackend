package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import c.example.aibouauth.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    private int quantity;
    @Getter
    private Double amount;
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private LocalDateTime date;


    public void setUser(User user) {
        this.user = user;
    }
    @Transient
    public Integer getUserId() {
        return user != null ? user.getId() : null;
    }

}
