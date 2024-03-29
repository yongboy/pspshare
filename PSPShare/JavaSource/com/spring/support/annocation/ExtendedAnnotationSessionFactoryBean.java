package com.spring.support.annocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.util.ClassUtils;

public class ExtendedAnnotationSessionFactoryBean extends
		AnnotationSessionFactoryBean {

	private String[] basePackages;
	private ClassLoader beanClassLoader;

	public void afterPropertiesSet() throws Exception {
		Collection<Class<?>> entities = new ArrayList<Class<?>>();
		ClassPathScanningCandidateComponentProvider scanner = this
				.createScanner();
		for (String basePackage : this.basePackages) {
			this.findEntities(scanner, entities, basePackage);
		}
		this.setAnnotatedClasses(entities
				.toArray(new Class<?>[entities.size()]));
		super.afterPropertiesSet();
	}

	private ClassPathScanningCandidateComponentProvider createScanner() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
				false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		return scanner;
	}

	private void findEntities(
			ClassPathScanningCandidateComponentProvider scanner,
			Collection<Class<?>> entities, String basePackage) {
		Set<BeanDefinition> annotatedClasses = scanner
				.findCandidateComponents(basePackage);
		for (BeanDefinition bd : annotatedClasses) {
			String className = bd.getBeanClassName();
			Class<?> type = ClassUtils.resolveClassName(className,
					this.beanClassLoader);
			entities.add(type);
		}
	}

	public void setBasePackage(String basePackage) {
		this.basePackages = new String[] { basePackage };
	}

	public void setBasePackages(String[] basePackages) {
		this.basePackages = basePackages;
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}
}