package security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    UserDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService(){ return new UserDetailsServiceImpl();
    }

    // Validation
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.authorizeRequests()
                .antMatchers("/profile", "/comment", "/favorite", "/checkout").authenticated()
                .antMatchers( "/order-history", "/ordered").hasRole("USER")
                .antMatchers("/manage-order").hasRole("SELLER")
                .and()
                .formLogin().loginPage("/login-form")
                .defaultSuccessUrl("/home")
                .loginProcessingUrl("/login")
                .failureUrl("/login-form?error")
                .usernameParameter("username").passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and().exceptionHandling().accessDeniedPage("/denied")
                .and().csrf().disable()
                .headers().disable(); // use iframe
    }
}
