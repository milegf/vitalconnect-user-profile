package com.vitalconnect.user_profile.controller;

//<editor-fold desc="IMPORTS">
import com.vitalconnect.user_profile.model.UserProfile;
import com.vitalconnect.user_profile.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//</editor-fold>

@RestController // Controller para solicitudes HTTP
@RequestMapping("/api/v1/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // SOLICITUDES HTTP

    // Crear nuevo perfil
    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@Valid @RequestBody UserProfile userProfile) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.createUserProfile(userProfile));
    }

    // Obtener todos los perfiles
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userProfileService.getAllUserProfiles());
    }

    // Obtener perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable int id) {
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        return ResponseEntity.ok(userProfile);
    }

    // Obtener especialidades de un usuario
    @GetMapping("/{id}/especialidades")
    public ResponseEntity<List<String>> getEspecialidades(@PathVariable int id) {
        return ResponseEntity.ok(userProfileService.obtenerEspecialidades(id));
    }

    // Actualizar perfil
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable int id, @Valid @RequestBody UserProfile userProfile) {
        return userProfileService.updateUserProfile(id, userProfile)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable int id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }


}
