package com.recruiting.pros.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/index**", "/", "/js/**", "/css/**", "/img/**", "/vendor/**", "/scss/**",
                        "/job", "/job1", "/job2", "/job3","/assets/**", "/buttons","/JobDetails","/retrieveData")
                .permitAll()
                .requestMatchers("/admin/**","/pages/**").hasAuthority("ADMIN")
                .requestMatchers("/employee/**").permitAll() // Allow access to /employee/registration without login
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .successHandler((request, response, authentication) -> {
                                    UserDetails user = (UserDetails) authentication.getPrincipal();
                                    boolean isAdmin = user.getAuthorities().stream()
                                            .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
                                    if (isAdmin) {
                                        response.sendRedirect("/admin");
                                    } else {
                                        response.sendRedirect("/");
                                    }

                                })
                                .permitAll()

                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL to trigger the logout
                        .logoutSuccessUrl("/") // Redirect to the home page after successful logout

                );

        // Return the SecurityFilterChain as a FilterChainProxy
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
