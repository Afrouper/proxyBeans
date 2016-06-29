package de.afrouper.beans.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.NoChangeTracking;

public final class BeanDescription implements Serializable {

	private static final long serialVersionUID = -1565943889462348220L;

	private final String name;

	private final Map<String, BeanProperty> properties;

	private final Class<? extends Bean> beanClass;

	private final boolean trackChanges;

	public BeanDescription(Class<? extends Bean> beanClass, String name) {
		this.beanClass = beanClass;
		this.trackChanges = beanClass.isAnnotationPresent(NoChangeTracking.class);
		this.name = name;
		properties = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public Class<? extends Bean> getBeanClass() {
		return beanClass;
	}

	public boolean isTrackChanges() {
		return trackChanges;
	}

	public BeanProperty getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public void addBeanProperty(BeanProperty property) {
		properties.put(property.getName(), property);
	}
}
