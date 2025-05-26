package com.vitalconnect.user_profile.repository;

//<editor-fold desc="IMPORTS">
import com.vitalconnect.user_profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//</editor-fold>

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    // Metodo para buscar un usuario por RUT
    Optional<UserProfile> findByRut(String rut);

    // Metodo para buscar un usuario por su estado
    List<UserProfile> findByActivoTrue();

}