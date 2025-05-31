package c.example.aibouauth.auth;

import c.example.aibouauth.config.JwtService;
import c.example.aibouauth.token.Token;
import c.example.aibouauth.token.TokenRepository;
import c.example.aibouauth.token.TokenType;
import c.example.aibouauth.user.User;
import c.example.aibouauth.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
//import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JavaMailSender javaMailSender;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .estVerifie(true)
                .maxAmount(0.00)
                .montant(BigDecimal.ZERO)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // Générer et enregistrer le code de confirmation
        //String confirmationCode = generateConfirmationCode();
        //user.setCodeConfirmation(confirmationCode);
        var savedUser = userRepository.save(user);
        //sendConfirmationEmail(savedUser.getEmail(), confirmationCode);
        //System.out.println("Code de confirmation à enregistrer : " + confirmationCode);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(savedUser.getRole())
                .firstName(savedUser.getFirstName())
                .montant(savedUser.getMontant())
                .email(savedUser.getEmail())
                .id(savedUser.getId())
                .build();
    }
    /*
    public void confirmRegistration(String email, String confirmationCode) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        // Ajoutez ces logs pour le débogage
        System.out.println("Code stocké dans la base de données : " + user.getCodeConfirmation());
        System.out.println("Code saisi par l'utilisateur : " + confirmationCode);

        if (user.isEstVerifie()) {
            throw new IllegalStateException("Le compte est déjà vérifié");
        }

        if (confirmationCode.equals(user.getCodeConfirmation())) {
            user.setEstVerifie(true);
            user.setCodeConfirmation(null); // Réinitialiser le code de confirmation après vérification
            userRepository.save(user);
        } else {
            throw new VerificationCodeException("Code de vérification invalide pour l'utilisateur avec l'email: " + email);
        }
    }/*

    /*
    public void resendVerificationCode(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        // Générer et enregistrer le nouveau code de confirmation
        String newConfirmationCode = generateConfirmationCode();
        user.setCodeConfirmation(newConfirmationCode);
        userRepository.save(user);

        // Renvoyer l'e-mail de confirmation avec le nouveau code
        sendConfirmationEmail(user.getEmail(), newConfirmationCode);
        System.out.println("Code stocké dans la base de données : " + user.getCodeConfirmation());

    }*/

    /*private String generateConfirmationCode() {
        // Génération d'un code de confirmation de 6 chiffres
        Random random = new Random();
        int confirmationNumber = 100000 + random.nextInt(900000);
        return String.valueOf(confirmationNumber);
    }*/

    private void sendConfirmationEmail(String email, String codeConfirmation) {
        // Logique pour envoyer un e-mail de confirmation
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Confirmation de l'inscription");
        message.setText("Votre code de confirmation est : " + codeConfirmation);

        javaMailSender.send(message);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + request.getEmail()));

        // Check if the user is verified
        if (!user.isEstVerifie()) {
            throw new UserNotVerifiedException("User not verified. Please verify your email.", HttpStatus.UNAUTHORIZED);
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .firstName(user.getFirstName())
                .montant(user.getMontant())
                .id(user.getId())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findUserByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + userEmail));

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
