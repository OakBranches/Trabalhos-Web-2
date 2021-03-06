package br.ufscar.dc.dsw1.config;

import br.ufscar.dc.dsw1.security.UsuarioDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UsuarioDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers("/login","/example", "/download/**", "/teste","/message", "/static/js/**","/results/**" ,"/js/**","/css/**", "/image/**", "/webjars/**", "/home").permitAll()
				.antMatchers("/proposta/list", "/proposta/create/**", "proposta/listar").hasRole("USER")
				.antMatchers("/cliente/**", "/loja/**").hasRole("ADMIN")
				.antMatchers("/carro/list", "/proposta/accept/**", "/proposta/reject/**", "/carro/create").hasRole("LOJA")
				// Controladores REST
				.antMatchers("/clientes", "/lojas").permitAll()
				.antMatchers("/clientes/{\\d+}", "/lojas/{\\d+}").permitAll()
				.antMatchers("/propostas/veiculos/{\\d+}").permitAll()
				.antMatchers("/propostas/clientes/{\\d+}").permitAll()
				.antMatchers("/veiculos/lojas/{\\d+}").permitAll()
				.antMatchers("/veiculos/fotos/{\\d+}").permitAll()
				.antMatchers("/veiculos/modelos/{\\w+}").permitAll()

				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll();

	}
}