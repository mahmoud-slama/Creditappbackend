package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import c.example.aibouauth.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


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
    @Getter
    private BigDecimal amount;
    @Getter

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String purchaseName;
    private int quantity;
    private LocalDateTime purchaseDate;



}
