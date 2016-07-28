package com.epam.configuration.initializer;

import com.epam.configuration.AppConfig;
import com.epam.configuration.HibernateConfiguration;
import com.epam.configuration.SpringSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initialization of Spring MVC servlet.
 */
public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppConfig.class, HibernateConfiguration.class, SpringSecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}