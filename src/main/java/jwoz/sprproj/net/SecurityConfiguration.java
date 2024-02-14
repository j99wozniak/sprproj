package jwoz.sprproj.net;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
                .withUsername("Aleander")
                .password(passwordEncoder().encode("pass"))
                .roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("pass!"))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/hello").permitAll()
                        .requestMatchers("/api/user").hasRole("USER")
                        .requestMatchers("/api/property").hasRole("USER") //
                        .requestMatchers("/api/show").hasRole("USER")     //
                        .requestMatchers("/api/create").hasRole("ADMIN")  //
                        .requestMatchers("/api/change").hasRole("ADMIN")  //
                        .requestMatchers("/api/delete").hasRole("ADMIN")  //
                        .requestMatchers("/api/createtable").hasRole("ADMIN")
                        .requestMatchers("/api/deletetable").hasRole("ADMIN")
                        .requestMatchers("/api/settablename").hasRole("ADMIN")
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}