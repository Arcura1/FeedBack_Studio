package org.example.feedbackstudio.login.user; // Veya konfigürasyon için tercih ettiğiniz paket

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // --- Herkese Açık (permitAll) Endpoint'ler ---

                        // Kullanıcı İşlemleri
                        .requestMatchers(HttpMethod.POST, "/api/users/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/getAll").permitAll() // UserPage için
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll() // Gerekirse kullanıcı detayı
                        .requestMatchers(HttpMethod.PUT, "/api/users/update/**").permitAll() // UserPage için (şimdilik permitAll)
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").permitAll() // UserPage için (şimdilik permitAll)
                        .requestMatchers(HttpMethod.POST, "/api/users/search").permitAll() // UserPage için (şimdilik permitAll)
                        .requestMatchers(HttpMethod.POST, "/api/users/blacklist").permitAll() // UserPage için (şimdilik permitAll)
                        .requestMatchers(HttpMethod.GET, "/api/users/blacklist/**").permitAll() // UserPage için (şimdilik permitAll)
                        .requestMatchers(HttpMethod.GET, "/api/users/email/**").permitAll() // UserPage için (şimdilik permitAll)
                        .requestMatchers(HttpMethod.GET, "/api/users/type/**").permitAll() // UserPage için (şimdilik permitAll)


                        // Rol İşlemleri (RolePageComponent ve diğerleri için)
                        .requestMatchers(HttpMethod.GET, "/roles").permitAll() // RolePage - loadRoles
                        .requestMatchers(HttpMethod.POST, "/roles").permitAll() // RolePage - createRole
                        .requestMatchers(HttpMethod.PUT, "/roles/**").permitAll() // RolePage - updateRole
                        .requestMatchers(HttpMethod.DELETE, "/roles/**").permitAll() // RolePage - deleteRole
                        .requestMatchers(HttpMethod.POST, "/roles/query").permitAll() // RolePage - searchRoles, AuthorityRoleComponent - onInputChange

                        // Yetki İşlemleri (AuthorityPageComponent ve AuthorityRoleComponent için)
                        .requestMatchers(HttpMethod.POST, "/authorities/query").permitAll() // AuthorityPageComponent - searchAuthorities, AuthorityRoleComponent - fetchAuthorities
                        // .requestMatchers(HttpMethod.GET, "/authorities").permitAll() // Gerekirse tüm yetkileri listeleme
                        // .requestMatchers(HttpMethod.POST, "/authorities").permitAll() // Gerekirse yetki oluşturma
                        // .requestMatchers(HttpMethod.PUT, "/authorities/**").permitAll() // Gerekirse yetki güncelleme
                        // .requestMatchers(HttpMethod.DELETE, "/authorities/**").permitAll() // Gerekirse yetki silme

                        // Rol-Yetki Eşleştirme İşlemleri (AuthorityRoleComponent için)
                        .requestMatchers(HttpMethod.GET, "/api/role-authorities").permitAll() // fetchAll
                        .requestMatchers(HttpMethod.POST, "/api/role-authorities").permitAll() // save (create)
                        .requestMatchers(HttpMethod.PUT, "/api/role-authorities/**").permitAll() // save (update)
                        .requestMatchers(HttpMethod.DELETE, "/api/role-authorities/**").permitAll() // delete

                        // Organizasyon İşlemleri (RolePageComponent ve UserPageComponent için)
                        .requestMatchers(HttpMethod.POST, "/organization/search").permitAll() // RolePage - onInputChange, UserPage - onInputChange
                        .requestMatchers(HttpMethod.GET, "/organization/{id}").permitAll() // RolePage - editRole içinde organizasyon adını almak için

                        // Diğer potansiyel public endpoint'ler (Swagger vb.)
                        // .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // OPTIONS isteklerine her zaman izin ver (CORS preflight için önemli)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // --- Kimlik Doğrulaması Gerektiren Endpoint'ler ---
                        // Yukarıda permitAll ile belirtilmeyen TÜM diğer istekler kimlik doğrulama gerektirir.
                        // Geliştirme tamamlandığında ve login mekanizmanız oturduğunda,
                        // yukarıdaki permitAll'ların çoğunu kaldırıp buraya düşmelerini sağlayacaksınız.
                        .anyRequest().authenticated()
                );
        // Eğer JWT tabanlı kimlik doğrulama kullanacaksanız:
        // http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        // ve JWT token'ını doğrulamak için bir JwtDecoder bean'i sağlamanız gerekir.

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Angular uygulamanızın adresi
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*")); // Geliştirme için tüm başlıklara izin ver
        configuration.setAllowCredentials(true);
        // Gerekirse istemcinin erişebileceği response header'ları:
        // configuration.setExposedHeaders(Arrays.asList("Authorization", "X-Custom-Header"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Tüm yollar ("/**") için bu CORS yapılandırmasını uygula
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}