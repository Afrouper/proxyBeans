package de.afrouper.beans.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.afrouper.beans.Bean;

public final class BeanDescription implements Serializable {

	private static final long serialVersionUID = -1565943889462348220L;

	private final String name;

	private final Map<String, BeanProperty> properties;

	private final Class<? extends Bean> beanClass;

	public BeanDescription(Class<? extends Bean> beanClass, String name) {
		this.beanClass = beanClass;
		this.name = name;
		properties = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public Class<? extends Bean> getBeanClass() {
		return beanClass;
	}

	public BeanProperty getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public void addBeanProperty(BeanProperty property) {
		properties.put(property.getName(), property);
	}
}
