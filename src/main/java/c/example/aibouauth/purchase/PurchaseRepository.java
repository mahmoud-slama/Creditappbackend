package c.example.aibouauth.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    List<Purchase> findAllByUserId(Integer user_id);
}
