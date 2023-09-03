package es.usal.coaching;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoachingApplication.class, args);
	}

	// @Bean
	// public CorsFilter corsFilter() {
	// 	CorsConfiguration config = new CorsConfiguration();
    //     config.addAllowedOrigin("http://localhost:4200"); // Cambia esta URL por el dominio de tu aplicación Angular
    //     config.addAllowedMethod("*");
    //     config.addAllowedHeader("*");
    //     config.setAllowCredentials(true); 

	// 	UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
	// 	urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);
	// 	return new CorsFilter(urlBasedCorsConfigurationSource);
	// }

	
}
