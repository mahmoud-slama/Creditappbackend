package c.example.aibouauth.purchase;


import c.example.aibouauth.product.ProductNotFoundException;
import c.example.aibouauth.user.User;
import c.example.aibouauth.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UsersService userService;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);


    @GetMapping("/admin")
    public ResponseEntity<List<Purchase>> getAdminPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Purchase>> getClientPurchases(@PathVariable Integer clientId) {
        User client = userService.getUserById(clientId);
        List<Purchase> purchases = purchaseService.getUserPurchases(client);
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/admin")
    public ResponseEntity<Object> createPurchaseByAdmin(@RequestBody Purchase purchase) {
        try {
            Purchase savedPurchase = purchaseService.savePurchaseByAdmin(purchase);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPurchase);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with name: " + purchase.getPurchaseName());
        } catch (PurchaseSaveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the purchase to the database");
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}