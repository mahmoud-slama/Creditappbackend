package c.example.aibouauth.purchase;


import c.example.aibouauth.user.User;
import c.example.aibouauth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

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

    @PostMapping("/purchase/{userId}")
    public ResponseEntity<User> addPurchasesToUser(@PathVariable Integer userId, @RequestBody List<Purchase> purchases) {
        User user = purchaseService.addPurchasesToUser(purchases, userId);
        return ResponseEntity.ok(user);
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
