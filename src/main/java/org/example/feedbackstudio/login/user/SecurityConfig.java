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

                        // Rol-yetki eşleştirme
                        .requestMatchers(HttpMethod.GET, "/api/role-authorities").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/role-authorities").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/role-authorities/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/role-authorities/**").permitAll()

                        // Homework işlemleri
                        .requestMatchers(HttpMethod.GET, "/Homework/getAll").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Homework/getByUser/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/Homework/add").permitAll()

                        // Classroom işlemleri
                        .requestMatchers(HttpMethod.GET, "/classrooms/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/classrooms").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/classrooms/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/classrooms/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/classrooms/byUserId/**").permitAll()

                        // Organization işlemleri
                        .requestMatchers(HttpMethod.GET, "/organization/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/organization").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/organization/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/organization/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/organization/search").permitAll()

                        .requestMatchers(HttpMethod.GET, "/Homework/getAllByT").permitAll()
                        .requestMatchers(HttpMethod.POST, "/Homework/add").permitAll()

                        // PDF işlemleri
                        .requestMatchers(HttpMethod.POST, "/pdf/findAllByHU").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pdf/addPdf").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pdf/uploadPdf").permitAll()

                        .requestMatchers(HttpMethod.POST, "/pdf/pdfById").permitAll()
                        .requestMatchers(HttpMethod.GET, "/viewAll/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/highlights/viewH/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/highlights").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/add").permitAll()
                        .requestMatchers(HttpMethod.GET, "/view").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/delAll/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/highlights/delAll/**").permitAll()

                        // ClassroomUser işlemleri
                        .requestMatchers(HttpMethod.GET, "/classroom-users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/classroom-users/byUser/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/classroom-users").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/classroom-users/**").permitAll()

                        // CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Geriye kalan tüm istekler için authentication gerekir
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
