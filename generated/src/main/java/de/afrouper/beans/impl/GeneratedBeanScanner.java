package de.afrouper.beans.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import de.afrouper.beans.GeneratedBean;

public class GeneratedBeanScanner {

	private static final GeneratedBeanScanner ref = new GeneratedBeanScanner();

	private Map<Class<? extends GeneratedBean>, GeneratedBeanDescription> scannedBeans;

	private GeneratedBeanScanner() {
		scannedBeans = new ConcurrentHashMap<>();
	}

	public static GeneratedBeanScanner get() {
		return ref;
	}

	GeneratedBeanDescription getBeanDescription(Class<? extends GeneratedBean> beanClass) {
		GeneratedBeanDescription descr = scannedBeans.get(beanClass);
		if (descr == null) {
			descr = createBeanDescription(beanClass);
			scannedBeans.put(beanClass, descr);
		}
		return descr;
	}

	private GeneratedBeanDescription createBeanDescription(Class<? extends GeneratedBean> beanClass) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			GeneratedBeanDescription description = new GeneratedBeanDescription(beanClass,
					beanInfo.getBeanDescriptor().getName());
			for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
				if (GeneratedBeanHelper.isValidProperty(property.getName())) {
					handlePropertyDescriptor(description, property);
				}
			}
			return description;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to create BeanDescription from beanClass " + beanClass.getName(),
					e);
		}
	}

	private void handlePropertyDescriptor(GeneratedBeanDescription description, PropertyDescriptor property) {
		BeanProperty beanProperty = new BeanProperty(property.getName());
		checkAndInvoke(property.getReadMethod(), beanProperty::setReadMethodName);
		checkAndInvoke(property.getWriteMethod(), beanProperty::setWriteMethodName);
		description.addBeanProperty(beanProperty);
	}

	private void checkAndInvoke(Method method, Consumer<String> consumer) {
		if (method != null && GeneratedBeanHelper.isValidMethod(method.getName())) {
			consumer.accept(method.getName());
		}

	}
}
