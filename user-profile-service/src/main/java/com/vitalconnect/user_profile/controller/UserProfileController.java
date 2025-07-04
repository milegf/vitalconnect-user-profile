package com.vitalconnect.user_profile.controller;

//<editor-fold desc="IMPORTS">
import com.vitalconnect.user_profile.model.UserProfile;
import com.vitalconnect.user_profile.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import com.vitalconnect.user_profile.assembler.UserProfileModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
//</editor-fold>

@Tag(name = "Perfiles de Usuario", description = "Controlador para la gestión de perfiles de usuario en la API de VitalConnect.")
@RestController // Controller para solicitudes HTTP
@RequestMapping("/api/v1/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileModelAssembler assembler;

    // SOLICITUDES HTTP

    // CREAR PERFIL
    @Operation(summary = "Crear un nuevo perfil de usuario",
               description = "Permite crear un nuevo perfil de usuario en la API de VitalConnect. Se requiere enviar los datos del perfil en el cuerpo de la solicitud en formato JSON.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil de usuario creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, datos del perfil incorrectos."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el perfil.")
    })
    @PostMapping
    public ResponseEntity<EntityModel<UserProfile>> createUserProfile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del perfil de usuario a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserProfile.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación de perfil",
                                    summary = "Nuevo usuario tipo paciente.",
                                    value = """
                        {
                            "nombre": "Tomás",
                            "apellido": "Reyes",
                            "rut": "22.333.444-5",
                            "correo": "tcastro@email.com",
                            "contraseña": "ClaveSegura456",
                            "rol": "PACIENTE",
                            "especialidades": [],
                            "activo": true
                        }
                        """
                            )
                    )
            )
            @Valid @RequestBody UserProfile userProfile
    ) {
        UserProfile createdProfile = userProfileService.createUserProfile(userProfile);
        EntityModel<UserProfile> entityModel = assembler.toModel(createdProfile);

        return ResponseEntity
                .created(linkTo(methodOn(UserProfileController.class).getUserProfileById(createdProfile.getId())).toUri())
                .body(entityModel);
    }



    // OBTENER TODOS LOS PERFILES
    @Operation(summary = "Listar todos los perfiles de usuario",
               description = "Recupera una lista con todos los perfiles registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfiles de usuario recuperada exitosamente.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserProfile.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al recuperar los perfiles.")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserProfile>>> getAllUserProfiles() {
        List<UserProfile> profiles = userProfileService.getAllUserProfiles();

        List<EntityModel<UserProfile>> profileModels = profiles.stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<UserProfile>> collectionModel = CollectionModel.of(
                profileModels,
                linkTo(methodOn(UserProfileController.class).getAllUserProfiles()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }



    // OBTENER PERFIL POR ID
    @Operation(summary = "Obtener perfil de usuario por ID",
               description = "Devuelve todos los datos de un usuario según el ID proporcionado. Útil para obtener información detallada de un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de usuario recuperado exitosamente.", content = @Content(schema = @Schema(implementation = UserProfile.class))),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese ID registrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al recuperar el perfil.")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<UserProfile>> getUserProfileById(
            @Parameter(name = "ID", description = "ID único del usuario que se desea consultar", required = true)
            @PathVariable int id
    ) {
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        EntityModel<UserProfile> entityModel = assembler.toModel(userProfile);
        return ResponseEntity.ok(entityModel);
    }




    // OBTENER PERFIL POR RUT
    @Operation(summary = "Obtener perfil de usuario por RUT",
               description = "Devuelve todos los datos de un usuario según el RUT proporcionado. Útil para obtener información detallada de un usuario cuando no se conoce el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de usuario recuperado exitosamente.",  content = @Content(schema = @Schema(implementation = UserProfile.class))),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese RUT registrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al recuperar el perfil.")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<EntityModel<UserProfile>> getUserProfileByRut(
            @Parameter(name = "RUT", description = "RUT del usuario a consultar. Debe tener el siguiente formato: 12.345.678-9", required = true)
            @PathVariable String rut
    ) {
        UserProfile userProfile = userProfileService.getUserProfileByRut(rut);
        EntityModel<UserProfile> entityModel = assembler.toModel(userProfile);
        return ResponseEntity.ok(entityModel);
    }



    // OBTENER USUARIOS ACTIVOS
    @Operation(summary = "Obtener usuarios activos",
               description = "Recupera una lista de todos los perfiles de usuario que están activos en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios activos recuperada exitosamente.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserProfile.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al recuperar los usuarios activos.")
    })
    @GetMapping("/active")
    public ResponseEntity<CollectionModel<EntityModel<UserProfile>>> obtenerUsuariosActivos() {
        List<UserProfile> usuarios = userProfileService.obtenerUsuariosActivos();

        List<EntityModel<UserProfile>> usuarioModels = usuarios.stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<UserProfile>> collectionModel = CollectionModel.of(
                usuarioModels,
                linkTo(methodOn(UserProfileController.class).obtenerUsuariosActivos()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }




    // Obtener especialidades de un usuario
    @Operation(summary = "Obtener especialidades de un usuario",
               description = "Recupera las especialidades asociadas a un perfil de usuario según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades recuperada exitosamente.", content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese ID registrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al recuperar las especialidades.")
    })
    @GetMapping("/{id}/especialidades")
    public ResponseEntity<CollectionModel<String>> getEspecialidades(
            @Parameter(name = "ID", description = "ID del usuario del cual se desea obtener la/s especialidad/es", required = true)
            @PathVariable int id
    ) {
        List<String> especialidades = userProfileService.obtenerEspecialidades(id);

        CollectionModel<String> collectionModel = CollectionModel.of(
                especialidades,
                linkTo(methodOn(UserProfileController.class).getEspecialidades(id)).withSelfRel(),
                linkTo(methodOn(UserProfileController.class).getUserProfileById(id)).withRel("usuario"),
                linkTo(methodOn(UserProfileController.class).getAllUserProfiles()).withRel("todos")
        );

        return ResponseEntity.ok(collectionModel);
    }




    // ACTUALIZAR PERFIL
    @Operation(summary = "Actualizar perfil de usuario",
               description = "Permite actualizar los datos de un perfil de usuario existente en la API de VitalConnect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de usuario actualizado exitosamente.", content = @Content(schema = @Schema(implementation = UserProfile.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, hay datos del perfil incorrectos."),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese ID registrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el perfil.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserProfile>> updateUserProfile(
            @Parameter(name = "ID", description = "ID del usuario cuyo perfil se desea actualizar", required = true)
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Se deben incluir todos los campos del perfil del usuario con el o los datos actualizados.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserProfile.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de actualización de perfil",
                                    summary = "Actualizar un perfil de usuario con nuevos datos. En este caso, agregar una segunda especialidad a una doctora.",
                                    value = """
                                    {
                                        "nombre": "Marta",
                                        "apellido": "Castro",
                                        "rut": "11.222.333-4",
                                        "correo": "marta.castro@hospital.cl",
                                        "contraseña": "ContraseñaSegura!123",
                                        "rol": "DOCTOR/A",
                                        "especialidades": ["Cardiología", "Pediatría"],
                                        "activo": true
                                    }
                                    """
                            )
                    )
            )
            @Valid @RequestBody UserProfile userProfile
    ) {
        UserProfile updated = userProfileService.updateUserProfile(id, userProfile);
        EntityModel<UserProfile> entityModel = assembler.toModel(updated);
        return ResponseEntity.ok(entityModel);
    }



    // ELIMINAR PERFIL
    @Operation(summary = "Eliminar perfil de usuario",
               description = "Permite eliminar un perfil de usuario existente en la API de VitalConnect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil de usuario eliminado exitosamente."),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese ID registrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el perfil.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(name = "id", description = "ID del usuario que se desea eliminar del sistema", required = true)
            @PathVariable int id
    ) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }

}
