package de.afrouper.beans.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.afrouper.beans.api.Bean;

public class BeanListInvocationHandler<E extends Bean> extends AbstractInvocationHandler {

	private final List<E> realList;

	public BeanListInvocationHandler(BeanDescription description, Class<E> elementType) {
		this.realList = new ArrayList<>();
	}

	@Override
	protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
		if (isListMethod(method)) {
			return method.invoke(realList, args);
		} else {
			return null;
		}
	}

	private boolean isListMethod(Method method) {
		return List.class.isAssignableFrom(method.getDeclaringClass());
	}
}
