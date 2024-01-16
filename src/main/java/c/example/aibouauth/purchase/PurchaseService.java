package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import c.example.aibouauth.product.ProductNotFoundException;
import c.example.aibouauth.product.ProductRepository;
import c.example.aibouauth.user.User;
import c.example.aibouauth.user.UserNotFoundException;
import c.example.aibouauth.user.UserRepository;
import c.example.aibouauth.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersService userService;
    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);

    public List<Purchase> getUserPurchases(User user) {
        return purchaseRepository.findByUser(user);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase createPurchase(String name, int quantity, User user) {
        Product product = productRepository.findByName(name);

        if (product == null) {
            throw new ProductNotFoundException("Product not found with name: " + name);
        }



        int availableQuantity = product.getQuantity();

        if (quantity > availableQuantity) {
            throw new IllegalArgumentException("Purchase quantity exceeds available product quantity");
        }

        BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        // Update the product quantity
        product.setQuantity(availableQuantity - quantity);
        productRepository.save(product);


        Purchase purchase = new Purchase();
        purchase.setPurchaseName(name);
        purchase.setQuantity(quantity);
        purchase.setAmount(amount);
        purchase.setProduct(product);
        purchase.setUser(user);
        purchase.setPurchaseDate(LocalDateTime.now());

        // Update client's montant
        userService.updateMontant(user, amount);

        purchaseRepository.save(purchase);



        try {
            return purchaseRepository.save(purchase);
        } catch (Exception e) {
            log.error("Error saving the purchase to the database", e);
            throw new RuntimeException("Error saving the purchase to the database", e);
        }
    }
}