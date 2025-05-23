package com.vitalconnect.user_profile.service;

//<editor-fold desc="IMPORTS">
import com.vitalconnect.user_profile.exception.ResourceNotFoundException;

import com.vitalconnect.user_profile.model.UserProfile;
import com.vitalconnect.user_profile.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//</editor-fold>

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // Crear nuevo perfil
    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    // Obtener todos los perfiles
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    // Obtener perfil por ID
    public UserProfile getUserProfileById(int id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado."));
    }

    // Obtener perfil por RUT
    public Optional<UserProfile> getUserProfileByRut(String rut) {
        return userProfileRepository.findByRut(rut);
    }

    // Obtener por especialidad
    @Transactional
    public List<String> obtenerEspecialidades(Integer id) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new ArrayList<>(user.getEspecialidades());
    }

    // Actualizar perfil
    public Optional<UserProfile> updateUserProfile(int id, UserProfile updatedProfile) {
        return userProfileRepository.findById(id).map(existing -> {
            existing.setNombre(updatedProfile.getNombre());
            existing.setApellido(updatedProfile.getApellido());
            // Atributo de RUT saltado, no se permite actualizarlo.
            existing.setEmail(updatedProfile.getEmail());
            existing.setEspecialidades(updatedProfile.getEspecialidades());
            existing.setRoles(updatedProfile.getRoles());
            return userProfileRepository.save(existing);
        });
    }

    // Eliminar perfil
    public boolean deleteUserProfile(int id) {
        if (userProfileRepository.existsById(id)) {
            userProfileRepository.deleteById(id);
            return true;
        }
        return false;
    }

}