package c.example.aibouauth.purchase;

import c.example.aibouauth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
        List<Purchase> findByUser(User user);

}
