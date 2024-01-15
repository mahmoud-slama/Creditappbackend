package c.example.aibouauth.purchase;

import c.example.aibouauth.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> getPurchaseById(Integer id) {
        return purchaseRepository.findById(id);
    }

    public Purchase createPurchase(Purchase purchase) {
        // Fetch the product from the purchase
        Product product = purchase.getProduct();

        // Calculate the amount based on quantity and product price
        double amount = purchase.getQuantity() * product.getPrice();

        // Set the calculated amount to the purchase object before saving
        purchase.setAmount(amount);

        // Save the purchase to the repository
        return purchaseRepository.save(purchase);
    }

    public Purchase updatePurchase(Integer id, Purchase updatedPurchase) {
        // Check if the purchase with the given id exists
        if (purchaseRepository.existsById(id)) {
            updatedPurchase.setId(id);
            return purchaseRepository.save(updatedPurchase);
        } else {
            // Handle purchase not found scenario
            return null;
        }
    }

    public void deletePurchase(Integer id) {
        purchaseRepository.deleteById(id);
    }
}
