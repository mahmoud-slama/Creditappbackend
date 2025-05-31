package c.example.aibouauth.user;

import c.example.aibouauth.token.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UsersService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private  final UserRepository repository;
    @Autowired
    private TokenRepository tokenRepository;
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

    public List<User> getAllUsers(){
        return  repository.findAll();
    }

    public User getUserById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }


    public User updateMaxAmount(UserDTO newUser, Integer id) {
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


}

