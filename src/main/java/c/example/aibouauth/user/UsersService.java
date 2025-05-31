package c.example.aibouauth.user;

import c.example.aibouauth.purchase.PurchaseRepository;
import c.example.aibouauth.token.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MailProperties.class)
@Configuration
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private  final UserRepository repository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired

    private JavaMailSender javaMailSender; // Injectez le JavaMailSender





    public List<User> getAllUsers(){
        return  repository.findAll();
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void updateMontant(User user, BigDecimal amount) {
        BigDecimal currentMontant = user.getMontant();

        if (currentMontant == null) {
            currentMontant = BigDecimal.ZERO;
        }

        user.setMontant(currentMontant.add(amount));
        repository.save(user);
    }


    public User updateMaxAmount(UserDto newUser, Integer id) {
        return repository.findById(id)
                .map(user->{
                    user.setMaxAmount(newUser.getMaxAmount());
                    return repository.save(user);
                })
                .orElseThrow(()->new UserNotFoundException(id));
    }


    @Transactional
    public void deleteUserById(Integer id) {

        tokenRepository.deleteByUserId(id);


        // Then delete the user
        repository.deleteById(id);
    }






    public void verifyAccount(String email, String code) {
        User user = repository.findByEmail(email);


        if (user.isEstVerifie()) {
            throw new IllegalStateException("Le compte est déjà vérifié");
        }

        if (code.equals(user.getCodeConfirmation())) {
            user.setEstVerifie(true);
            repository.save(user);
        } else {
            throw new IllegalStateException("Code de vérification invalide");
        }
    }
}

