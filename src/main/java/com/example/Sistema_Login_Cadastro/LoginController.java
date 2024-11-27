package com.example.Sistema_Login_Cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        return userRepository.findByUsername(username)
                .map(user -> {
                    boolean success = user.getPassword().equals(password); // Lembre-se de usar BCrypt para produção
                    return ResponseEntity.ok(Map.of("success", (Object) success)); // Cast para Object
                })
                .orElse(ResponseEntity.ok(Map.of("success", (Object) false))); // Cast para Object
    }
}
