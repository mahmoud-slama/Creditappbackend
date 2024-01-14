package c.example.aibouauth.purchase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/purchases")
    public ResponseEntity<List<Purchase>> getAllPurchases(@RequestParam(required = false) String productName) {
        try {
            List<Purchase> purchases = new ArrayList<>();

            if (productName == null)
                purchases.addAll(purchaseRepository.findAll());
            else
                purchases.addAll(purchaseRepository.findByProductName(productName));

            if (purchases.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(purchases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/purchases/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseByPurchaseId(@PathVariable int purchaseId) {
        Optional<Purchase> purchaseData = purchaseRepository.findById(purchaseId);
        return purchaseData.map(purchase -> new ResponseEntity<>(purchase, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/purchases/{id}")
    public ResponseEntity<HttpStatus> deletePurchase(@PathVariable int id) {
        try {
            purchaseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/purchases")
    public ResponseEntity<Purchase> createPurchase(
            @RequestParam String productName,
            @RequestParam Integer quantity,
            @RequestParam Integer userId) {

        try {
            Purchase createdPurchase = purchaseService.createPurchase(productName, quantity, userId);
            return new ResponseEntity<>(createdPurchase, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
