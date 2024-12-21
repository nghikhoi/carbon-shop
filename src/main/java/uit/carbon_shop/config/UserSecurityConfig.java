package uit.carbon_shop.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uit.carbon_shop.service.UserTokenService;
import uit.carbon_shop.service.UserUserDetailsService;


@Configuration
public class UserSecurityConfig {

    @Bean
    public AuthenticationManager userAuthenticationManager(final PasswordEncoder passwordEncoder,
            final UserUserDetailsService userUserDetailsService) {
        final DaoAuthenticationProvider userAuthenticationManager = new DaoAuthenticationProvider(passwordEncoder);
        userAuthenticationManager.setUserDetailsService(userUserDetailsService);
        return new ProviderManager(userAuthenticationManager);
    }

    @Bean
    public JwtRequestFilter userRequestFilter(final UserUserDetailsService userUserDetailsService,
            final UserTokenService userTokenService) {
        return new JwtRequestFilter(userUserDetailsService, userTokenService);
    }

    @Bean
    @Order(10)
    public SecurityFilterChain userFilterChain(final HttpSecurity http,
            @Qualifier("userAuthenticationManager") final AuthenticationManager userAuthenticationManager,
            @Qualifier("userRequestFilter") final JwtRequestFilter userRequestFilter) throws
            Exception {
        return http.securityMatcher("/api/buyer/**", "/api/user/**", "/api/file/**")
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .authenticationManager(userAuthenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(userRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
