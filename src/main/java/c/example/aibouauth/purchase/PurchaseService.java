package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import c.example.aibouauth.product.ProductNotFoundException;
import c.example.aibouauth.product.ProductRepository;
import c.example.aibouauth.user.User;
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


    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);

    public List<Purchase> getUserPurchases(User user) {
        return purchaseRepository.findByUser(user);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase savePurchaseByAdmin(Purchase purchase) {
        Product product = productRepository.findByName(purchase.getPurchaseName());

        if (product == null) {
            throw new ProductNotFoundException("Product not found with name: " + purchase.getPurchaseName());
        }

        BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity()));

        purchase.setProduct(product);
        purchase.setAmount(amount);
        purchase.setPurchaseDate(LocalDateTime.now());

        try {
            return purchaseRepository.save(purchase);
        } catch (Exception e) {

            log.error("Error saving the purchase to the database", e);


            throw new RuntimeException("Error saving the purchase to the database", e);
        }
    }}