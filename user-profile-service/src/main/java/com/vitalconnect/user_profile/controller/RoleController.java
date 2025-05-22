package com.vitalconnect.user_profile.controller;

//<editor-fold desc="IMPORTS">
import com.vitalconnect.user_profile.model.UserProfile;
import com.vitalconnect.user_profile.repository.UserProfileRepository;
import com.vitalconnect.user_profile.service.UserProfileService;
import com.vitalconnect.user_profile.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//</editor-fold>

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    // Obtiene los roles de un usuario
    @GetMapping("/{id}")
    public ResponseEntity<List<String>> getRoles(@PathVariable Integer id) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return ResponseEntity.ok(user.getRoles());
    }

    // Agrega un nuevo rol al usuario
    @PostMapping("/{id}")
    public ResponseEntity<List<String>> addRole(@PathVariable Integer id, @RequestBody String nuevoRol) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        List<String> roles = user.getRoles();
        if (!roles.contains(nuevoRol)) {
            roles.add(nuevoRol);
            userProfileRepository.save(user);
        }
        return ResponseEntity.ok(roles);
    }

    // Elimina un rol específico del usuario
    @DeleteMapping("/{id}/{rol}")
    public ResponseEntity<List<String>> removeRole(@PathVariable Integer id, @PathVariable String rol) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        List<String> roles = user.getRoles();
        roles.removeIf(r -> r.equalsIgnoreCase(rol));
        userProfileRepository.save(user);
        return ResponseEntity.ok(roles);
    }
}
