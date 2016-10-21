package de.afrouper.beans.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.NoChangeTracking;

final class BeanDescription implements Serializable {

	private static final long serialVersionUID = -1565943889462348220L;

	private final String name;

	private final Map<String, BeanProperty> properties;

	private final Class<? extends Bean> beanClass;

	private final boolean trackChanges;

	BeanDescription(Class<? extends Bean> beanClass, String name) {
		this.beanClass = beanClass;
		this.trackChanges = beanClass.isAnnotationPresent(NoChangeTracking.class);
		this.name = name;
		properties = new HashMap<>();
	}

	String getName() {
		return name;
	}

	Class<? extends Bean> getBeanClass() {
		return beanClass;
	}

	boolean isTrackChanges() {
		return trackChanges;
	}

	BeanProperty getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	void addBeanProperty(BeanProperty property) {
		properties.put(property.getName(), property);
	}
}
