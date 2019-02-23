package org.saleban.carparts.config;

import org.apache.jasper.security.SecurityUtil;
import org.saleban.carparts.service.UserSecurityService;
import org.saleban.carparts.util.Mappings;
import org.saleban.carparts.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityService;

    private BCryptPasswordEncoder passwordEncoder(){
        return SecurityUtility.passwordEncoder();
    }

    private static final String[] PUBLIC_MACTCHES = {
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/fonts/**",
            "/register",
            "/newUser",
            "/forgetPassword",
            "/settings",
            "/login",
            "/buy",
            "/products"
    };

    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http.authorizeRequests().antMatchers(PUBLIC_MACTCHES).permitAll().anyRequest().authenticated();
        http.csrf().ignoringAntMatchers("/products").and()
                .formLogin().failureUrl("/login?error")
                //.defaultSuccessUrl(Mappings.HOME)
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
//        http.authorizeRequests()
//                //Homepage
//                .antMatchers("/", "/products").permitAll()
//                //Static Resource
//                .antMatchers(PUBLIC_MACTCHES).permitAll()
//                .antMatchers("/store/view/**", "/user/view/**", "/store/products/*", "/product/**").permitAll()
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                .usernameParameter("username")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .deleteCookies("remember-me")
//                .logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//                .rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

}
