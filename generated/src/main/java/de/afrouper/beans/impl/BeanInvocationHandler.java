package de.afrouper.beans.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.afrouper.beans.api.ext.BeanAccess;
import de.afrouper.beans.api.ext.BeanVisitor;

class BeanInvocationHandler extends AbstractInvocationHandler implements BeanAccess {

	private final Map<String, BeanValue> values;

	private final BeanDescription beanDescription;

	private final Set<String> changedProperties;

	BeanInvocationHandler(BeanDescription beanDescription) {
		this.beanDescription = beanDescription;
		values = new ConcurrentHashMap<>();
		changedProperties = Collections.synchronizedSet(new HashSet<>());
	}

	@Override
	protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
		return handleBeanMethod(method.getName(), args);
	}

	@Override
	public void resetTrackedChanges() {
		changedProperties.clear();
	}

	@Override
	public void visit(BeanVisitor visitor, boolean onlyChangedProperties) {
	}

	@Override
	public int hashCode() {
		return values.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!Proxy.isProxyClass(object.getClass())) {
			return false;
		}
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(object);
		if (!BeanInvocationHandler.class.isAssignableFrom(invocationHandler.getClass())) {
			return false;
		}
		BeanInvocationHandler other = (BeanInvocationHandler) invocationHandler;
		if (!beanDescription.getBeanClass().equals(other.beanDescription.getBeanClass())) {
			return false;
		}
		return values.equals(other.values);
	}

	@Override
	public String toString() {
		return beanDescription.getBeanClass().getName() + " - " + values.toString();
	}

	private Object handleBeanMethod(String methodName, Object[] args) {
		if (BeanUtil.isSetterMethod(methodName)) {
			setValue(BeanUtil.getPropertyNameFromMethodName(methodName), args[0]);
			return null;
		} else if (BeanUtil.isGetterMethod(methodName)) {
			return getValue(BeanUtil.getPropertyNameFromMethodName(methodName)).getValue();
		}
		throw new IllegalArgumentException("Method " + methodName + " cannot be invoked.");
	}

	private BeanValue getValue(String propertyName) {
		BeanProperty property = beanDescription.getProperty(propertyName);
		if (property.getReadMethodName() != null) {
			BeanValue value = values.get(propertyName);
			if (value == null) {
				value = new BeanValue(null);
				values.put(propertyName, value);
			}
			return value;
		}
		throw new IllegalArgumentException("Cannot access property " + propertyName + ". ReadMethodName is null.");
	}

	private void setValue(String propertyName, Object object) {
		BeanProperty property = beanDescription.getProperty(propertyName);
		if (property.getWriteMethodName() != null) {
			BeanValue value = values.get(propertyName);
			if (value == null) {
				value = new BeanValue(object);
				values.put(propertyName, value);
				changedProperties.add(propertyName);
			} else if (valueChanged(value.getValue(), object)) {
				value.setValue(object);
				changedProperties.add(propertyName);
			}
		} else {
			throw new IllegalArgumentException("Cannot access property " + propertyName + ". WriteMethodName is null.");
		}
	}

	private boolean valueChanged(Object oldValue, Object newValue) {
		if (oldValue != null) {
			return !oldValue.equals(newValue);
		} else {
			return newValue != null;
		}
	}
}
