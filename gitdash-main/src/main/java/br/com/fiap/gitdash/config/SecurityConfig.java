package br.com.fiap.gitdash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/", "/login").permitAll() // Rota de login é pública
                    .anyRequest().authenticated() // Qualquer outra rota requer autenticação
            )
            .oauth2Login(); // Habilita login OAuth2

        return http.build();
    }
    
    @ModelAttribute("user")
    public OidcUser getUser(@AuthenticationPrincipal OidcUser principal) {
        return principal;
    }
}
