package c.example.aibouauth.user;


import c.example.aibouauth.purchase.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UsersService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
       @RequestBody changePasswordRequest request,
       Principal connectedUser
    ){
        service.changePassword(request,connectedUser);
        return  ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // New endpoint to add a purchase to a user
    @PostMapping("/{user_id}/purchases")
    public ResponseEntity<User> addPurchaseToUser(@PathVariable Integer user_id, @RequestBody Purchase purchase) {
        User updatedUser = service.addPurchaseToUser(user_id, purchase);
        return ResponseEntity.ok(updatedUser);
    }

}
