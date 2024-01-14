package c.example.aibouauth.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    List<Purchase> findAllByUser_Id(Integer userId);

    List<Purchase> findByProductName(String productName);

}
