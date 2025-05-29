package org.example.feedbackstudio.login.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Kullanıcı işlemleri
                        .requestMatchers(HttpMethod.POST, "/api/users/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/getAll").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/update/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/blacklist").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/blacklist/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/email/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/type/**").permitAll()

                        // Rol işlemleri
                        .requestMatchers(HttpMethod.GET, "/roles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/roles").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/roles/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/roles/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/roles/query").permitAll()

                        // Yetki işlemleri
                        .requestMatchers(HttpMethod.POST, "/authorities/query").permitAll()

                        // Rol-yetki eşleştirmeleri
                        .requestMatchers(HttpMethod.GET, "/api/role-authorities").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/role-authorities").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/role-authorities/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/role-authorities/**").permitAll()

                        // Organizasyon işlemleri
                        .requestMatchers(HttpMethod.GET, "/organization/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/organization").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/organization/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/organization/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/organization/search").permitAll()

                        // CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Diğer her şey için kimlik doğrulama gerekir
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
