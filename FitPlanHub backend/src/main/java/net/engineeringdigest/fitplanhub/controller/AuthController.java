package net.engineeringdigest.fitplanhub.controller;

import net.engineeringdigest.fitplanhub.model.User;
import net.engineeringdigest.fitplanhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup/user")
    public String userSignup(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already exists";
        }

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/signup/trainer")
    public String trainerSignup(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already exists";
        }

        user.setRole("TRAINER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "Trainer registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginData) {

        User user = userRepository.findByEmail(loginData.getEmail());

        if (user == null) {
            return "Invalid email";
        }

        if (!passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
            return "Invalid password";
        }
        return user.getRole() + ":" + user.getId();
    }
}
