package c.example.aibouauth.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Integer productId) {
        super("Product not found with ID: " + productId);
    }}
