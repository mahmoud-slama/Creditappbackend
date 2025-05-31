package c.example.aibouauth.user;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private  final UsersService service;


    @PatchMapping
    public ResponseEntity<?> changePassword(
       @RequestBody changePasswordRequest request,
       Principal connectedUser
    ){
        service.changePassword(request,connectedUser);
        return  ResponseEntity.ok().build();
    }


    @PutMapping("/user/maxAmount/{id}")
    public ResponseEntity<UserDTO> updateMaxAmount(@RequestBody UserDTO updatedUserData, @PathVariable Integer id) {
        return Optional.ofNullable(service.getUserById(id))
                .map(existingUser -> {
                    service.updateMaxAmount(updatedUserData, id);
                    return ResponseEntity.ok(UserDTO.fromEntity(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/user/maxAmount/{id}")
    public ResponseEntity<Double> getMaxAmount(@PathVariable Integer id) {
        Optional<User> userOptional = Optional.ofNullable(service.getUserById(id));

        return userOptional.map(user -> ResponseEntity.ok(user.getMaxAmount()))
                .orElse(ResponseEntity.notFound().build());
    }




}
