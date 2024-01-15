package c.example.aibouauth.purchase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final Logger log = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public List<Purchase> getAllPurchases() {
        log.info("Getting all purchases");
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Integer id) {
        log.info("Getting purchase by ID: {}", id);
        Optional<Purchase> purchase = purchaseService.getPurchaseById(id);
        return purchase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody Purchase purchase) {
        log.info("Creating a new purchase: {}", purchase);
        Purchase createdPurchase = purchaseService.createPurchase(purchase);
        log.info("Purchase created: {}", createdPurchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Integer id, @RequestBody Purchase updatedPurchase) {
        log.info("Updating purchase with ID {}: {}", id, updatedPurchase);
        Purchase purchase = purchaseService.updatePurchase(id, updatedPurchase);
        if (purchase != null) {
            log.info("Purchase updated: {}", purchase);
            return ResponseEntity.ok(purchase);
        } else {
            log.info("Purchase with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer id) {
        log.info("Deleting purchase with ID: {}", id);
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}