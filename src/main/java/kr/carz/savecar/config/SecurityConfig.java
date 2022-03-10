package kr.carz.savecar.config;

import kr.carz.savecar.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminService adminService;
    private final AuthFailureHandler customFailureHandler;

    @Autowired
    public SecurityConfig(AdminService adminService, AuthFailureHandler customFailureHandler){
        this.adminService = adminService;
        this.customFailureHandler = customFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // 인증을 무시하기 위한 설정
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/index", "/admin/signup", "/admin/login").permitAll()
                .antMatchers("/admin/campingcar/**", "/admin/counsel/**", "/admin/discount/**", "/admin/moren/**", "/admin/setting/**", "/admin/popup/**", "/admin/rentcar/**", "/admin/image/**").hasRole("ADMIN") // ADMIN only
                .and()
                .formLogin()     // 로그인 설정
                .loginPage("/admin/login")      // 커스텀 login 페이지를 사용
                .defaultSuccessUrl("/admin/index")      // 로그인 성공 시 이동할 페이지
                .failureHandler(customFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                .logoutSuccessUrl("/admin/index")
                .invalidateHttpSession(true)    // 세션 초기화
                .and()
                .exceptionHandling();

        http
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(passwordEncoder());
    }
}