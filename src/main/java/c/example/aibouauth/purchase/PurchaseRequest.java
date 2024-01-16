package c.example.aibouauth.purchase;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    private String name;
    private int quantity;
    private Integer userId;


}
