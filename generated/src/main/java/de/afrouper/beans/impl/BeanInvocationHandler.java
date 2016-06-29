package de.afrouper.beans.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanInvocationHandler extends AbstractInvocationHandler {

	private final Map<String, BeanValue> values;
	private final BeanDescription beanDescription;

	public BeanInvocationHandler(BeanDescription beanDescription) {
		this.beanDescription = beanDescription;
		values = new ConcurrentHashMap<>();
	}

	@Override
	protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
		return handleBeanMethod(method.getName(), args);
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
		if (JavaBeanUtil.isSetterMethod(methodName)) {
			setValue(JavaBeanUtil.getPropertyNameFromMethodName(methodName), args[0]);
			return null;
		} else if (JavaBeanUtil.isGetterMethod(methodName)) {
			return getValue(JavaBeanUtil.getPropertyNameFromMethodName(methodName)).getValue();
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
			} else {
				value.setValue(object);
			}
		} else {
			throw new IllegalArgumentException("Cannot access property " + propertyName + ". WriteMethodName is null.");
		}
	}

}
