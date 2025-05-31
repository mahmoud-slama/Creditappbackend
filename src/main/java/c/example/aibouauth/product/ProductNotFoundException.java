package c.example.aibouauth.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer id) {
        super("could not found product with id "+ id );
    }
}
