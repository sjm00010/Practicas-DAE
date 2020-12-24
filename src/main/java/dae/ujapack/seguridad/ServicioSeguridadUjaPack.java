package dae.ujapack.seguridad;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Proveedor de datos de seguridad de UjaPack
 * @author sjm00010
 */
@Configuration
@EnableWebSecurity
public class ServicioSeguridadUjaPack extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").roles("ADMIN").password("{noop}admin")
            .and()
            .withUser("operario").roles("OPERARIO").password("{noop}secret");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        http.httpBasic();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/envio").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/ujapack/envio/**").hasAnyRole("OPERARIO");
    }
    
    
    
}
