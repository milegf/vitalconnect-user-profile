package com.vitalconnect.user_profile.model;

// <editor-fold desc="IMPORTS">
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Modelo de perfil de usuario para la API de VitalConnect")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremental de Id
    @Schema(description = "ID único del usuario. Se crea de forma automática en la base de datos.",
            example = "101",
            accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @NotBlank(message = "Es obligatorio registrar un nombre.")
    @Size(max = 100)
    @Schema(description = "Nombre del usuario",
            example = "Juan")
    private String nombre;

    @NotBlank(message = "Es obligatorio registrar un apellido.")
    @Size(max = 100)
    @Schema(description = "Apellido del usuario",
            example = "Pérez")
    private String apellido;

    @NotBlank(message = "Es obligatorio registrar un rut.")
    @Column(unique = true)
    @Size(max = 13)
    @Schema(description = "RUT del usuario, debe ser único.",
            example = "12.345.678-9")
    private String rut;

    @NotBlank(message = "Es obligatorio registrar un correo.")
    @Size(max = 255, message = "El correo no puede exceder los 255 caracteres.")
    @Column(unique = true)
    @Schema(description = "Correo electrónico del usuario, debe ser único.",
            example = "juan.perez@email.com")
    private String correo;

    @NotBlank(message = "Es obligatorio registrar una contraseña.")
    @Size(min = 12, message = "La contraseña debe tener al menos 12 caracteres.")
    @Schema(description = "Contraseña del usuario, debe tener al menos 12 caracteres.",
            example = "ContraseñaSegura!123")
    private String contrasena;

    @NotBlank(message = "Es obligatorio registrar el rol del usuario.")
    @Column(name = "rol")
    @Size(max = 70, message = "El rol no puede exceder los 70 caracteres.")
    @Schema(description = "Rol del usuario en el sistema, por ejemplo: 'PACIENTE', 'DOCTOR/A', etc.",
            example = "PACIENTE")
    private String rol;

    @ElementCollection
    @CollectionTable(name = "user_specialties", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "especialidad")
    @Schema(description = "Lista de especialidades del usuario, aplicable para roles como 'DOCTOR/A'.",
            example = "[\"Cardiología\", \"Pediatría\"]")
    private List<String> especialidades;

    @NotBlank(message = "Es obligatorio registrar si es un usuario que está activo en el sistema.")
    @Column(name = "activo")
    @Schema(description = "Indica si es un perfil activo",
            example = "true")
    private boolean activo = true;
}