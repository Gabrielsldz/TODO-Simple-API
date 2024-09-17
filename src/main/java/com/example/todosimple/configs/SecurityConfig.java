package com.example.todosimple.configs;

import com.example.todosimple.security.JWTAuthenticationFilter;
import com.example.todosimple.security.JWTAuthorizationFilter;
import com.example.todosimple.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JWTUtil jwtUtil;




    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDetailsService userDetailsService;


    private static final String[] PUBLIC_MATCHERS = {
            "/"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/user",
            "/login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable(); // CORS e CSRF desabilitados

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        this.authenticationManager = authenticationManagerBuilder.build();

        http.authorizeHttpRequests()  // Atualizado para authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // requestMatchers substitui antMatchers
                .requestMatchers(PUBLIC_MATCHERS).permitAll() // URLs públicas
                .anyRequest().authenticated().and().authenticationManager(authenticationManager); // Qualquer outra requisição precisa estar autenticada

        http.addFilter(new JWTAuthenticationFilter(this.authenticationManager, jwtUtil)); // Adiciona o filtro de autenticação JWT
        http.addFilter(new JWTAuthorizationFilter(this.authenticationManager, jwtUtil, this.userDetailsService)); // Adiciona o filtro de autorização JWT

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Sessões stateless para JWT
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
