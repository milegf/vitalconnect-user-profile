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
        if ("PACIENTE".equalsIgnoreCase(userProfile.getRol())) {
            if (userProfile.getEspecialidades() != null && !userProfile.getEspecialidades().isEmpty()) {
                throw new IllegalArgumentException("Un paciente no puede tener especialidades.");
            }
        }
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
    public UserProfile getUserProfileByRut(String rut) {
        return userProfileRepository.findByRut(rut)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con RUT " + rut + " no encontrado."));
    }

    // Obtener usuarios activos
    public List<UserProfile> obtenerUsuariosActivos() {
        List<UserProfile> usuariosActivos = userProfileRepository.findByActivoTrue();
        if (usuariosActivos.isEmpty()) {
            throw new ResourceNotFoundException("No hay usuarios activos registrados.");
        }
        return usuariosActivos;
    }

    // Obtener por especialidad
    @Transactional
    public List<String> obtenerEspecialidades(Integer id) {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuarios no encontrados."));

        return new ArrayList<>(user.getEspecialidades());
    }

    // Actualizar perfil
    public Optional<UserProfile> updateUserProfile(int id, UserProfile updatedProfile) {
        return userProfileRepository.findById(id).map(existing -> {
            existing.setNombre(updatedProfile.getNombre());
            existing.setApellido(updatedProfile.getApellido());
            // Atributo de RUT saltado, no se permite actualizarlo.
            existing.setCorreo(updatedProfile.getCorreo());
            existing.setEspecialidades(updatedProfile.getEspecialidades());
            existing.setRol(updatedProfile.getRol());
            return userProfileRepository.save(existing);
        });
    }

    // Eliminar perfil
    public void deleteUserProfile(int id) {
        if (!userProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado.");
        }
        userProfileRepository.deleteById(id);
    }
}