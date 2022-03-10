package ru.project.musicbandsearch.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.project.musicbandsearch.services.UsersService;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // инъекция БД
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UsersService usersService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("graf").password(passwordEncoder().encode("2321")).roles("musician");
//
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select nickname, password, 'true' from users_table " +
                                "where nickname=?")
                .authoritiesByUsernameQuery(
                        "select nickname, roles.role from users_table " +
                                "inner join roles on users_table.role = roles.id where nickname=?");

//        auth.userDetailsService(usersService).passwordEncoder(NoOpPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
//                .antMatchers("/api/v1/profile").hasAnyRole("musician", "band", "costumer")
                .antMatchers("/api/v1/signup/**").permitAll()
//                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/api/v1/login")
                .loginProcessingUrl("/api/v1/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/api/v1/profile")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

//        http.csrf().ignoringAntMatchers("/db-console/*");
//        http.headers()
//                .frameOptions()
//                .sameOrigin();
    }
}