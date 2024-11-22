package it.testCatalogo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import it.testCatalogo.dao.ProdottoDao;
import it.testCatalogo.dao.impl.ProdottoDaoImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "it.testCatalogo.controller")
@PropertySource("classpath:catalogue.properties")
@EnableTransactionManagement
public class AppConfig implements WebMvcConfigurer {
	
	@Autowired
	private Environment env;

	
	//SEZIONE CONFIG PRESENTATION LAYER
	@Bean
	public FreeMarkerViewResolver getFreeMarkerResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		
		return resolver;
	}
	
	@Bean
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath("/WEB-INF/views/");
		
		return config;
	}
	
	
	//SEZIONE CONFIG ORM
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		
		ds.setDriverClassName(env.getRequiredProperty("catalogue-db-driver"));
		ds.setUrl(env.getRequiredProperty("catalogue-db-url"));
		ds.setUsername(env.getRequiredProperty("catalogue-db-username"));
//		ds.setPassword(env.getRequiredProperty("catalogue-db-password"));
		
		return ds;
	}
	
	//JPA && HIBERNATE
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(getDataSource());
		factory.setJpaVendorAdapter(adapter);
		factory.setPackagesToScan(getClass().getPackage().getName());
		
		factory.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class);  // Impostato esplicitamente altrimenti mi da error, non capisco perché. Forse perchè Spring Data JPA 3.x è compatibile con Hibernate 5.x e io sto usando la 6
	
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager getTransactionManager() {
		return new JpaTransactionManager(getEntityManagerFactory().getObject());
	}
	
	
	@Bean
	public ProdottoDao getProdottoDao() {
		return new ProdottoDaoImpl();
	}
	
	
	//Per poter utilizzare file .css per gli ftl bisogna fare l'override del seguente metodo dopo aver implementato la classe con  WebMvcConfigurer. Nel file .ftl andremo ad inserire: <link href="/resources/css/index.css" rel="stylesheet" type="text/css">
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**") // URL pattern per accedere alle risorse
                .addResourceLocations("/resources/"); // Percorso fisico nella directory webapp
    }
}
