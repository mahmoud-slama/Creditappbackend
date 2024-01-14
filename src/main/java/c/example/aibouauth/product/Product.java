package c.example.aibouauth.product;

import c.example.aibouauth.purchase.Purchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Integer product_id;
    private String name;
    private double price;
    private String ref;
    private Integer quantity;
    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;



}


