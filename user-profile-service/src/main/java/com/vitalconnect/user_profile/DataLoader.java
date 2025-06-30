package com.vitalconnect.user_profile;

import com.vitalconnect.user_profile.model.UserProfile;
import com.vitalconnect.user_profile.repository.UserProfileRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private  UserProfileRepository repository;

    List<String> roles = List.of("paciente", "doctor/a", "enfermera/o");
    List<String> especialidades = List.of(
            "Cardiología", "Dermatología", "Pediatría", "Neurología", "Ginecología",
            "Traumatología", "Psiquiatría", "Otorrinolaringología", "Reumatología", "Oncología"
    );


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es", "CL"));
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            UserProfile user = new UserProfile();
            user.setNombre(faker.name().firstName());
            user.setApellido(faker.name().lastName());
            user.setRut(faker.idNumber().valid());
            user.setCorreo(faker.internet().emailAddress());
            user.setContrasena(faker.internet().password(12, 20, true, true, true));
            String rol = faker.options().nextElement(roles);
            user.setRol(rol);
            if (!rol.equals("paciente")) {
                Collections.shuffle(especialidades);
                user.setEspecialidades(List.of(especialidades.get(0)));
            } else {
                user.setEspecialidades(Collections.emptyList());
            }
            user.setActivo(faker.bool().bool());
            repository.save(user);
        }
    }



}
