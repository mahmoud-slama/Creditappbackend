package c.example.aibouauth.purchase;


import c.example.aibouauth.product.ProductRepository;
import c.example.aibouauth.user.User;
import c.example.aibouauth.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Method to get all purchases
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Transactional
    public User addPurchasesToUser(List<Purchase> purchases, Integer userId) {
        User user = userRepository.findById(userId.IntegerValue())
                .orElseThrow(() -> new RuntimeException("User not found"));
        double totalAmount = 0.0;

        for (Purchase purchase : purchases) {
            // Fetch product price based on the product name
            String productName = purchase.getName();
            double productPrice = productRepository.findByName(productName)
                    .map(Product::getPrice)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            purchase.setAmount(productPrice);
            purchase.setUser(user);
            purchaseRepository.save(purchase);

            totalAmount += productPrice;
        }

        user.setTotalAmount(user.getTotalAmount() + totalAmount); // Update user's total amount
        userRepository.save(user); // Save the user

        return user;
    }


}
