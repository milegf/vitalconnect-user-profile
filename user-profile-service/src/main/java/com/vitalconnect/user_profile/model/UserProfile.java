package com.vitalconnect.user_profile.model;

// <editor-fold desc="IMPORTS">
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
// </editor-fold>

// Anotaciones JPA para la creación de tabla.
@Entity
@Table(name = "user_profiles")
// Anotaciones Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremental de Id
    private int id;

    @NotBlank(message = "Es obligatorio registrar un nombre.")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "Es obligatorio registrar un apellido.")
    @Size(max = 100)
    private String apellido;

    @NotBlank(message = "Es obligatorio registrar un rut.")
    @Column(unique = true)
    @Size(max = 13)
    private String rut;

    @NotBlank(message = "Es obligatorio registrar un correo.")
    @Size(max = 255, message = "El correo no puede exceder los 255 caracteres.")
    @Column(unique = true)
    private String correo;

    @NotBlank(message = "Es obligatorio registrar una contraseña.")
    @Size(min = 12, message = "La contraseña debe tener al menos 12 caracteres.")
    private String contrasena; // Para otras entregas se puede trabajar en el cifrado y seguridad.

    @Column(name = "rol")
    @Size(max = 70, message = "El rol no puede exceder los 70 caracteres.")
    private String rol;

    @ElementCollection
    @CollectionTable(name = "user_specialties", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "especialidad")
    private List<String> especialidades;

    @Column(name = "activo")
    private boolean activo = true;
}