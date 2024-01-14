package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import c.example.aibouauth.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


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
    private String productName;
    private LocalDate date;
    private Integer quantity;
    @Getter
    private Double amount;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public void setUser(User user) {
        this.user = user;
    }
    @Transient
    public Integer getUserId() {
        return user != null ? user.getId() : null;
    }

}
