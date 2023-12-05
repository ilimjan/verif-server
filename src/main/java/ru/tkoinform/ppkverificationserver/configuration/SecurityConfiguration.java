package ru.tkoinform.ppkverificationserver.configuration;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import ru.tkoinform.ppkverificationserver.Role;
import ru.tkoinform.ppkverificationserver.configuration.atjwt.JwkSetUriAtJwtDecoderBuilder;
import ru.tkoinform.ppkverificationserver.configuration.ppk.PpkJwtDecoder;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                //.decoder(decoder()
                                .decoder(ppkDecoder())
                        )
                )
                .authorizeRequests(authorize -> authorize
                        .antMatchers(HttpMethod.OPTIONS).permitAll()

                        .antMatchers(
                                "/",
                                "/*", // страницы типа /requests/something не будут работать (если все в одном war)

                                "/api/file/image/*", // изображения доступны всем по гуиду

                                "/swagger-resources/**",
                                "/swagger-ui/",
                                "/swagger-ui.html",
                                "/v2/api-docs",
                                "/webjars/**",

                                "/favicon.ico",
                                "/**/*.png",
                                "/**/*.gif",
                                "/**/*.svg",
                                "/**/*.jpg",
                                "/**/*.jpeg",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js",
                                "/**/*.vue",
                                "/**/*.pdf"
                        )
                        .permitAll()

                        .antMatchers(
                                "/api/**"
                        )
                        .authenticated()

                        .anyRequest().authenticated()
                );
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(Role.rolePrefix);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * Корректный декодер с валидацией issuer, времени истечения и подписи.
     * Сейчас вместо него используется PpkJwtDecoder
     *
     * @return JwtDecoder
     */
    private JwtDecoder decoder() {
        List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
        validators.add(new JwtTimestampValidator());
        validators.add(new JwtIssuerValidator(issuer));

        //NimbusJwtDecoderJwkSupport decoder = new NimbusJwtDecoderJwkSupport(jwkSetUri);
        //NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder jwkSetUriJwtDecoderBuilder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri);
        //NimbusJwtDecoder jwtDecoder = jwkSetUriJwtDecoderBuilder.build();

        JwkSetUriAtJwtDecoderBuilder jwkSetUriJwtDecoderBuilder = new JwkSetUriAtJwtDecoderBuilder(jwkSetUri);
        NimbusJwtDecoder jwtDecoder = jwkSetUriJwtDecoderBuilder.build();

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(validators));
        return jwtDecoder;
    }

    @Bean
    public JwtDecoder ppkDecoder() {
        return new PpkJwtDecoder();
    }
}
