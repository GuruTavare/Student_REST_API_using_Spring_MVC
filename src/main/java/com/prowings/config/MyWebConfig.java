package com.prowings.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.prowings.Interceptor.MyWebInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.prowings")
public class MyWebConfig implements WebMvcConfigurer{

	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql://localhost:3306/first_student_rest_api");
		source.setUsername("root");
		source.setPassword("Guru5798@");
		return source;
	}

	private Properties readHibernateProps() {

		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "update");

		return props;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("com.prowings.entity");
		bean.setHibernateProperties(readHibernateProps());
		return bean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		 // Register interceptor for specific controller method
        registry.addInterceptor(new MyWebInterceptor())
                .addPathPatterns("/v1/*"); 
	}
	
	
}
