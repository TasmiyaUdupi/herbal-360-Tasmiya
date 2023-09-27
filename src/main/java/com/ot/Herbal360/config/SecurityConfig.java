package com.ot.Herbal360.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.ot.Herbal360.filter.JwtFilter;
import com.ot.Herbal360.service.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtFilter filter;

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**", "/authenticate/login",
			"/password/verifyEmailBeforeUpdatePassword/{email}", "/password/forget/{uuid}/{newPassword}", "/admin/save",
			"/admin/id/{id}", "/admin/getalladmin", "/admin/deleteadmin/{id}", "/admin/updateadmin",
			"/admin/getadminbyname/{userName}", "/admin/email/{email}", "/admin/phone/{phone}",
			"/admin/validate/email/{email}/{password}", "/admin/validate/phone/{phone}/{password}", "/admin/otp",
			"/customer/save",  "/customer/getallcustomer", "/customer/deletecustomer/{id}",
			"/customer/updatecustomer", "/customer/getcustomerbyname/{customerName}", "/customer/email/{email}",
			"/customer/phone/{phone}", "/customer/getAllCustomers/{offset}/{pageSize}/{field}",
			"/admin/getAllAdmins/{offset}/{pageSize}/{field}", "/plan/save", "/plan/id/{id}", "/plan/getallplan",
			"/plan/deleteplan/{id}", "/plan/updateplan", "/plan/getplanbyname/{name}",
			"/plan/getAllPlans/{offset}/{pageSize}/{field}", "/product/save", "/product/id/{id}",
			"/product/getallproduct", "/product/deleteproduct/{id}", "/product/updateproduct",
			"/product/getproductbyname/{name}","/product/getAllProducts/{offset}/{pageSize}/{field}" };

	private static final String[] ADMIN_WHITELIST = {"/customer/id/{id}"};

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("Admin realm");
		return entryPoint;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().exceptionHandling().and().httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
				.antMatchers(AUTH_WHITELIST).permitAll().antMatchers(ADMIN_WHITELIST).hasRole("ADMIN").anyRequest()
				.authenticated();

		httpSecurity.authenticationProvider(authenticationProvider());

		httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}
}