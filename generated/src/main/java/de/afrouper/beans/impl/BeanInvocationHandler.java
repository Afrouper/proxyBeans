package de.afrouper.beans.impl;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.ext.BeanAccess;
import de.afrouper.beans.api.ext.BeanVisitor;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class BeanInvocationHandler extends AbstractInvocationHandler implements BeanImpl, BeanAccess {

	private static final boolean isJava8 = System.getProperty("java.version").startsWith("1.8");

	private final Map<String, BeanValue> values;

	private final BeanDescription beanDescription;

	private final Set<String> changedProperties;

	private final List<BeanListener> listeners;

	BeanInvocationHandler(BeanDescription beanDescription) {
		this.beanDescription = beanDescription;
		values = new ConcurrentHashMap<>();
		changedProperties = Collections.synchronizedSet(new HashSet<>());
		listeners = new ArrayList<>();
	}

	@Override
	protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
		return handleBeanMethod(proxy, method, args);
	}

	@Override
	public void resetTrackedChanges() {
		changedProperties.clear();
	}

	@Override
	public void visit(BeanVisitor visitor, boolean onlyChangedProperties) {
		Set<String> elements;
		if (onlyChangedProperties) {
			elements = changedProperties;
		} else {
			elements = beanDescription.getBeanProperties();
		}

		for (String propertyName : elements) {
			BeanValue value = values.get(propertyName);
			BeanProperty beanProperty = beanDescription.getProperty(propertyName);
			if (value != null) {
				BeanImpl impl = BeanUtil.getBeanImpl(value.getValue());
				if (impl != null) {
					if (Bean.class.isInstance(value.getValue())) {
						visitor.beanStart(propertyName, (Class<? extends Bean>) beanProperty.getType(), beanProperty.getAnnotations());
						impl.visit(visitor, onlyChangedProperties);
						visitor.beanEnd(propertyName);
					} else {
						//must be a list
						DelegatingList list = (DelegatingList) value.getValue();
						visitor.listStart(propertyName, list.getElementType(), beanProperty.getAnnotations());
						impl.visit(visitor, onlyChangedProperties);
						visitor.listEnd(propertyName);
					}
				} else {
					visitor.property(propertyName, value.getValue(), beanProperty.getType(), beanProperty.getAnnotations());
				}
			}
		}
	}

	@Override
	public void addBeanListener(BeanListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeBeanListener(BeanListener listener) {
		listeners.remove(listener);
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

	private Object handleBeanMethod(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		if (BeanUtil.isSetterMethod(methodName)) {
			setValue(BeanUtil.getPropertyNameFromMethodName(methodName), args[0]);
			return null;
		} else if (BeanUtil.isGetterMethod(methodName)) {
			return getValue(BeanUtil.getPropertyNameFromMethodName(methodName)).getValue();
		} else if (method.isDefault()) {
			final Class<?> declaringClass = method.getDeclaringClass();
			if(isJava8) {
				Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
				constructor.setAccessible(true);
				return constructor.newInstance(declaringClass)
						.in(declaringClass)
						.unreflectSpecial(method, declaringClass)
						.bindTo(proxy)
						.invokeWithArguments(args);
			}
			else {
				return MethodHandles.lookup()
						.findSpecial(declaringClass, methodName, MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass)
						.bindTo(proxy)
						.invokeWithArguments(args);
			}
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
				checkAndAddListener(value, property, null);
				values.put(propertyName, value);
				changedProperties.add(propertyName);
			} else if (isValueChanged(value.getValue(), object)) {
				value.setValue(object);
				checkAndAddListener(value, property, value.getValue());
				changedProperties.add(propertyName);
			}
		} else {
			throw new IllegalArgumentException("Cannot access property " + propertyName + ". WriteMethodName is null.");
		}
	}

	private void checkAndAddListener(BeanValue beanValue, BeanProperty property, Object oldValue) {
		BeanImpl beanImpl = BeanUtil.getBeanImpl(beanValue.getValue());
		if (beanImpl != null) {
			BeanListener oldListener = beanValue.getListener();
			if (oldListener != null) {
				BeanUtil.getBeanImpl(oldValue).removeBeanListener(oldListener);
				beanImpl.addBeanListener(oldListener);
			} else {
				beanValue.setListener(() -> childChanged(property));
				beanImpl.addBeanListener(beanValue.getListener());
			}
		}
	}

	private void childChanged(BeanProperty property) {
		changedProperties.add(property.getName());
	}

	private boolean isValueChanged(Object oldValue, Object newValue) {
		if (oldValue != null) {
			return !oldValue.equals(newValue);
		} else {
			return newValue != null;
		}
	}
}
