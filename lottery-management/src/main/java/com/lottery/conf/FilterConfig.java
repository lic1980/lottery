package com.lottery.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.lottery.filter.AgentAuthFilter;
import com.lottery.filter.CustomerAuthFilter;


@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<CorsFilter> registerCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    
	@Bean
    public FilterRegistrationBean<AgentAuthFilter> registerAgentAuthFilter() {
        FilterRegistrationBean<AgentAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AgentAuthFilter());
        registration.addUrlPatterns("/api/v1/agents/+");
        registration.setName("agentAuthFilter");
        return registration;
    }
	
	@Bean
    public FilterRegistrationBean<CustomerAuthFilter> registerCustomerAuthFilter() {
        FilterRegistrationBean<CustomerAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CustomerAuthFilter());
        registration.addUrlPatterns("/api/v1/customers/+");
        registration.setName("customerAuthFilter");
        return registration;
    }
	
	
}
