package com.vocaltech.api.config.security;

import com.vocaltech.api.model.Role;
import com.vocaltech.api.model.User;
import com.vocaltech.api.repository.IRoleRepository;
import com.vocaltech.api.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class PreRegisteredUsersLoader implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public PreRegisteredUsersLoader(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) { // Solo insertar si no hay usuarios
            Optional<Role> adminVosYTuVozRole = roleRepository.findByName("ROLE_ADMIN_VOS_Y_TU_VOZ");
            Optional<Role> adminNoCountryRole = roleRepository.findByName("ROLE_ADMIN_NO_COUNTRY");

            if (adminVosYTuVozRole.isPresent() && adminNoCountryRole.isPresent()) {
                User adminVYV = new User();
                adminVYV.setEmail("adminVYV@example.com");
                adminVYV.setPassword(passwordEncoder.encode("adminVYV123!"));
                adminVYV.setActive(true);
                adminVYV.setRoles(Set.of(adminVosYTuVozRole.get()));

                User adminNC = new User();
                adminNC.setEmail("adminNC@example.com");
                adminNC.setPassword(passwordEncoder.encode("adminNC123!"));
                adminNC.setActive(true);
                adminNC.setRoles(Set.of(adminNoCountryRole.get()));

                userRepository.saveAll(Set.of(adminVYV, adminNC));
                System.out.println("Usuarios pre-registrados insertados en la base de datos.");
            } else {
                System.err.println("No se encontraron los roles. Asegúrate de que existen en la base de datos.");
            }
        }
    }
}
