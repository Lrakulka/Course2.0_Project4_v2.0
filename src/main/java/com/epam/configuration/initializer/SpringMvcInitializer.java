package com.epam.configuration.initializer;

import com.epam.configuration.AppConfig;
import com.epam.configuration.DatabaseConfiguration;
import com.epam.configuration.SpringSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initialization of Spring MVC servlet.
 */
public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppConfig.class, DatabaseConfiguration.class, SpringSecurityConfig.class};
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