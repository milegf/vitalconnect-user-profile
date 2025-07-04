package com.vitalconnect.user_profile.assembler;

import com.vitalconnect.user_profile.controller.UserProfileController;
import com.vitalconnect.user_profile.model.UserProfile;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserProfileModelAssembler implements RepresentationModelAssembler<UserProfile, EntityModel<UserProfile>> {

    @Override
    public EntityModel<UserProfile> toModel(UserProfile profile) {
        return EntityModel.of(profile,
                linkTo(methodOn(UserProfileController.class).getUserProfileById(profile.getId())).withSelfRel(),
                linkTo(methodOn(UserProfileController.class).getUserProfileByRut(profile.getRut())).withRel("por-rut"),
                linkTo(methodOn(UserProfileController.class).getAllUserProfiles()).withRel("todos-usuarios"),
                linkTo(methodOn(UserProfileController.class).deleteById(profile.getId())).withRel("eliminar")
        );
    }
}
