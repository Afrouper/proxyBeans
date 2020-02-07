package de.afrouper.beans.api.ext;

import de.afrouper.beans.api.Bean;

import java.lang.annotation.Annotation;

public interface BeanVisitor {

	void property(String name, Object value, Class<?> type, Annotation[] annotations);

	void beanStart(String name, Class<? extends Bean> beanClass, Annotation[] annotations);

	void beanEnd(String name);

	void listStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations);

	void beanStartInArray(int index, Class<? extends Bean> beanClass, Annotation[] annotations);

	void beanEndInArray(int index);

	void listEnd(String name);

	void setStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations);

	void setEnd(String name);
}
