package com.example.Sistema_Login_Cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Listar usuários
    @GetMapping("/users")
    public ResponseEntity<?> listUsers(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Viewer")) {
            return ResponseEntity.status(403).body("Acesso negado");
        }
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Editar usuário (somente Admin)
    @PutMapping("/users/{id}")
    public ResponseEntity<?> editUser(@RequestBody User userDetails, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(403).body("Acesso negado");
        }
        return userRepository.findById(userDetails.getId())
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    userRepository.save(user);
                    return ResponseEntity.ok("Usuário atualizado com sucesso");
                })
                .orElse(ResponseEntity.status(404).body("Usuário não encontrado"));
    }

    // Excluir usuário (somente Admin)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return ResponseEntity.status(403).body("Acesso negado");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuário excluído com sucesso");
    }
}

