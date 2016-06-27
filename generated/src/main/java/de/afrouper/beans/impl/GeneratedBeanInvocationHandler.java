package de.afrouper.beans.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratedBeanInvocationHandler implements InvocationHandler {

	private final Map<String, BeanValue> values;
	private final GeneratedBeanDescription beanDescription;

	public GeneratedBeanInvocationHandler(GeneratedBeanDescription beanDescription) {
		this.beanDescription = beanDescription;
		values = new ConcurrentHashMap<>();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		switch (methodName) {
		case "hashCode":
			return hashCodeImpl();
		case "equals":
			return equalsImpl(args[0]);
		case "toString":
			return toStringImpl();
		default:
			return handleBeanMethod(methodName, args);
		}
	}

	private int hashCodeImpl() {
		return values.hashCode();
	}

	private Object equalsImpl(Object object) {
		if (object == null) {
			return false;
		}
		if(!Proxy.isProxyClass(object.getClass())) {
			return false;
		}
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(object);
		if (!GeneratedBeanInvocationHandler.class.isAssignableFrom(invocationHandler.getClass())) {
			return false;
		}
		GeneratedBeanInvocationHandler other = (GeneratedBeanInvocationHandler) invocationHandler;
		if (!beanDescription.getBeanClass().equals(other.beanDescription.getBeanClass())) {
			return false;
		}
		return values.equals(other.values);
	}

	private String toStringImpl() {
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
		}
		else {
			throw new IllegalArgumentException("Cannot access property " + propertyName + ". WriteMethodName is null.");
		}
	}

}
