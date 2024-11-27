package com.example.Sistema_Login_Cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Usuário já existe"));
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Hash de senha em produção
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "Usuário cadastrado com sucesso"));
    }
}
