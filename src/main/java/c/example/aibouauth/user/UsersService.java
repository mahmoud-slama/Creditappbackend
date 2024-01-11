package c.example.aibouauth.user;

import c.example.aibouauth.purchase.Purchase;
import c.example.aibouauth.purchase.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private  final UserRepository repository;
    private final PurchaseRepository purchaseRepository;
    public void changePassword(changePasswordRequest request, Principal connectedUser) {

        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        //check if the current password is correct
        if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        //check if the two new password are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {

            throw new IllegalStateException("Password are not the same ");
        }
        // update the password
        user.setPassword(passwordEncoder.encode((request.getNewPassword())));
        //save the new password
        repository.save(user);
    }

    @Transactional
    public User addPurchaseToUser(Integer user_id, Purchase purchase) {
        User user = repository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        purchase.setUser(user);
        purchaseRepository.save(purchase);
        return user;
    }

    // Method to get all purchases for a user
    public List<Purchase> getAllPurchasesByUser(Integer userId) {
        return purchaseRepository.findAllByUserId(userId);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}

