package c.example.aibouauth.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
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
}
