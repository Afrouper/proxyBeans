package de.afrouper.beans.impl;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.NoChangeTracking;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

final class BeanScanner {

	private static final BeanScanner ref = new BeanScanner();

	private Map<Class<? extends Bean>, BeanDescription> scannedBeans;

	private BeanScanner() {
		scannedBeans = new ConcurrentHashMap<>();
	}

	static BeanScanner get() {
		return ref;
	}

	BeanDescription getBeanDescription(Class<? extends Bean> beanClass) {
		BeanDescription descr = scannedBeans.get(beanClass);
		if (descr == null) {
			descr = createBeanDescription(beanClass);
			scannedBeans.put(beanClass, descr);
		}
		return descr;
	}

	private BeanDescription createBeanDescription(Class<? extends Bean> beanClass) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			BeanDescription description = new BeanDescription(beanClass, beanInfo.getBeanDescriptor().getName());
			for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
				if (BeanHelper.isValidProperty(property.getName())) {
					handlePropertyDescriptor(description, property);
				}
			}
			return description;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to create BeanDescription from beanClass " + beanClass.getName(),
					e);
		}
	}

	private void handlePropertyDescriptor(BeanDescription description, PropertyDescriptor property) {
		Method readMethod = property.getReadMethod();
		Method writeMethod = property.getWriteMethod();

		boolean trackChanges = hasAnnotation(readMethod, NoChangeTracking.class);

		BeanProperty beanProperty = new BeanProperty(property.getName(), property.getPropertyType(), trackChanges);
		checkAndInvoke(readMethod, beanProperty::setReadMethodName);
		checkAndInvoke(writeMethod, beanProperty::setWriteMethodName);
		if (readMethod != null) {
			beanProperty.setAnnotations(readMethod.getAnnotations());
		}
		description.addBeanProperty(beanProperty);
	}

	private boolean hasAnnotation(Method method, Class<? extends Annotation> annotation) {
		if (method != null) {
			return method.isAnnotationPresent(annotation);
		} else {
			return false;
		}
	}

	private void checkAndInvoke(Method method, Consumer<String> consumer) {
		if (method != null && BeanHelper.isValidMethod(method.getName())) {
			consumer.accept(method.getName());
		}

	}
}
