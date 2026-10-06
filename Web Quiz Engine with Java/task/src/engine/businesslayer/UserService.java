package engine.businesslayer;

import engine.dto.UserDTO;
import engine.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(UserDTO userDTO) {
        AppUser appUser = new AppUser(
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword())
        );
        AppUser saved = this.userRepository.save(appUser);

    }

    public boolean isUserExists(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
