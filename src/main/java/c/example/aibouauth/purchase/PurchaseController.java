package c.example.aibouauth.purchase;


import c.example.aibouauth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to add a new purchase
    public Purchase createPurchase(Purchase purchase, Integer user_id) {
        return userRepository.findById(user_id).map(user -> {
            purchase.setUser(user);
            return purchaseRepository.save(purchase);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Method to get all purchases
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    // Method to get purchases by user
    public List<Purchase> getPurchasesByUser(Integer user_id) {
        return purchaseRepository.findAllByUserId(user_id);
    }
}
