package d.dina.musiclibrary.configs;

import d.dina.musiclibrary.entities.Role;
import d.dina.musiclibrary.entities.User;
import d.dina.musiclibrary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {

            if (userRepository.count() == 0) {

                User employee = User.builder()
                        .username("dina")
                        .password(passwordEncoder.encode("dina123"))
                        .role(Role.EMPLOYEE)
                        .build();

                User manager = User.builder()
                        .username("eda")
                        .password(passwordEncoder.encode("eda123"))
                        .role(Role.MANAGER)
                        .build();

                User admin = User.builder()
                        .username("edra")
                        .password(passwordEncoder.encode("edra123"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.saveAll(List.of(employee, manager, admin));
            }
        };
    }
}
