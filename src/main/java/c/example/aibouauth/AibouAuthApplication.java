package c.example.aibouauth;

import c.example.aibouauth.auth.AuthenticationService;
import c.example.aibouauth.auth.RegisterRequest;
import c.example.aibouauth.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static c.example.aibouauth.user.Role.ADMIN;
import static c.example.aibouauth.user.Role.MANAGER;

@SpringBootApplication

@AllArgsConstructor
public class AibouAuthApplication {

	private  final UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(AibouAuthApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstName("admin")
					.lastName("admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.phone("99999999")
					.estVerifie(true)
					.build();

			// Check if the user with the same email already exists
			if (userRepository.findUserByEmail(admin.getEmail()).isEmpty()) {
				System.out.println("Admin token: " + service.register(admin).getAccessToken());
			} else {
				//System.out.println("User with email 'admin@mail.com' already exists.\n "+ userRepository.findUserByEmail("admin@mail.com").get().getTokens());
			}

			var manager = RegisterRequest.builder()
					.firstName("manager")
					.lastName("manager")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.estVerifie(true)

					.phone("99999999")
					.build();

			// Check if the user with the same email already exists
			if (userRepository.findUserByEmail(manager.getEmail()).isEmpty()) {
				System.out.println("Manager token: " + service.register(manager).getAccessToken());
			} else {
				//System.out.println("User with email 'manager@mail.com' already exists.\n "+ userRepository.findUserByEmail("manager@mail.com").get().getTokens());
			}

			;

		};
	}
}