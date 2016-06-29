package de.afrouper.beans.api.ext;

import java.lang.annotation.Annotation;

import de.afrouper.beans.api.Bean;

public interface BeanVisitor {

	void property(String name, Object value, Class<?> type, Annotation[] annotations);

	void beanStart(String name, Class<? extends Bean> beanClass, Annotation[] annotations);

	void beanEnd(String name);

	void listStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations);

	void listEnd(String name);
}
