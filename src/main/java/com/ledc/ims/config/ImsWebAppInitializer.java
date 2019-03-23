package com.ledc.ims.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * 配置DispatcherServlet
 */
public class ImsWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected String[] getServletMappings() {

		return new String[] {"/*.html"};
	}
	@Override
	protected Class<?>[] getRootConfigClasses (){

		return new Class<?>[] {};
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class<?>[] {};
	}

}
