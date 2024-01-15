package c.example.aibouauth.purchase;



import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class PurchaseRequest {
        private String productName;
        private Integer quantity;
        private Integer userId;

    }


