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
import uit.carbon_shop.service.MediatorTokenService;
import uit.carbon_shop.service.MediatorUserDetailsService;


@Configuration
public class MediatorSecurityConfig {

    @Bean
    public AuthenticationManager mediatorAuthenticationManager(
            final PasswordEncoder passwordEncoder,
            final MediatorUserDetailsService mediatorUserDetailsService) {
        final DaoAuthenticationProvider mediatorAuthenticationManager = new DaoAuthenticationProvider(passwordEncoder);
        mediatorAuthenticationManager.setUserDetailsService(mediatorUserDetailsService);
        return new ProviderManager(mediatorAuthenticationManager);
    }

    @Bean
    public JwtRequestFilter mediatorRequestFilter(
            final MediatorUserDetailsService mediatorUserDetailsService,
            final MediatorTokenService mediatorTokenService) {
        return new JwtRequestFilter(mediatorUserDetailsService, mediatorTokenService);
    }

    @Bean
    @Order(20)
    public SecurityFilterChain mediatorFilterChain(final HttpSecurity http,
            @Qualifier("mediatorAuthenticationManager") final AuthenticationManager mediatorAuthenticationManager,
            @Qualifier("mediatorRequestFilter") final JwtRequestFilter mediatorRequestFilter) throws
            Exception {
        return http.securityMatcher("/dummy/mediator")
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .authenticationManager(mediatorAuthenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(mediatorRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
