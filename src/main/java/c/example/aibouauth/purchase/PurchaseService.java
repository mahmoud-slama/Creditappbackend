package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import c.example.aibouauth.product.ProductRepository;
import c.example.aibouauth.user.User;
import c.example.aibouauth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;





@Service
public class PurchaseService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase createPurchase(String productName, Integer quantity, Integer userId) {
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));



        checkAndReduceProductStock(product, quantity);

        double amount = product.getPrice() * quantity;
        user.setMontant(user.getMontant() + amount);
        userRepository.save(user);

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setUser(user);
        purchase.setAmount(amount);
        purchase.setDate(purchase.getDate() != null ? purchase.getDate() : LocalDate.now());

        return purchaseRepository.save(purchase);
    }

    private void checkAndReduceProductStock(Product product, int purchaseQuantity) {
        if (product.getQuantity() < purchaseQuantity) {
            throw new RuntimeException("Insufficient stock available");
        }

        product.setQuantity(product.getQuantity() - purchaseQuantity);
        productRepository.save(product);
    }
}
