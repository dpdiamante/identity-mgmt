package dpd.accenture.identity.configs;

import dpd.accenture.identity.repository.UserRepository;
import dpd.accenture.identity.security.BasicAuthenticationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BasicAuthenticationPoint basicAuthenticationPoint;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("dpd.accenture.identity.web.controllers"))
                .paths(PathSelectors.any())
                .build();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/", "/api/**").permitAll()
                /*.antMatchers("/", "/user/admin/**").access("hasRole('ADMIN')")*/
                .anyRequest().authenticated();

        http.httpBasic().authenticationEntryPoint(basicAuthenticationPoint);

    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        /*UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);*/

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                dpd.accenture.identity.domain.User user = repository.findByUsername(username);
                if(user != null) {
                    return new User(user.getUsername(), user.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(user.isAdmin() ? "ADMIN" : "USER"));
                } else {
                    throw new UsernameNotFoundException("could not find the user '"
                            + username + "'");
                }
            }

        };

    }

}
