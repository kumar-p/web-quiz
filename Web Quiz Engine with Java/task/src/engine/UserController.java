package engine;

import engine.businesslayer.UserService;
import engine.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDTO user) {
        if (this.userService.isUserExists(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("User already exists!");
        }

        userService.addUser(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
