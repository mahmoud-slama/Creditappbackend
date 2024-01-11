package c.example.aibouauth.purchase;


import c.example.aibouauth.user.UserRepository;
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

    // Method to get all purchases
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}
