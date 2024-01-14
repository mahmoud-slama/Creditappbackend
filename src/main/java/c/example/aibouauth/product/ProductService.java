package c.example.aibouauth.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Optional<Double> getPriceOfProduct(String name) {
        return productRepository.findByName(name)
                .map(Product::getPrice);
    }
}
