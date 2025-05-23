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
import jakarta.persistence.FetchType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;
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
    @Size(max = 255, message = "El mail no puede exceder los 255 caracteres.")
    @Column(unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER) //Permite la carga anticipada de datos
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "rol")
    private List<String> roles;

    @ElementCollection
    @CollectionTable(name = "user_specialties", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "especialidad")
    private List<String> especialidades;

    @Column(name = "activo")
    private boolean activo = true;
}