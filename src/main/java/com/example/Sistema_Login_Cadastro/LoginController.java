package com.example.Sistema_Login_Cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        return userRepository.findByUsername(username)
                .map(user -> {
                    boolean success = user.getPassword().equals(password); // Use hashing para produção
                    Map<String, Object> responseMap = new HashMap<>();
                    if (success) {
                        responseMap.put("success", true);
                        responseMap.put("username", user.getUsername());
                        responseMap.put("role", user.getRole());
                    } else {
                        responseMap.put("success", false);
                    }
                    return ResponseEntity.ok(responseMap);
                })
                .orElseGet(() -> {
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("success", false); // Usuário não encontrado
                    return ResponseEntity.ok(responseMap);
                });
    }
}
