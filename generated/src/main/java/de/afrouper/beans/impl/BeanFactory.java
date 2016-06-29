package de.afrouper.beans.impl;

import java.lang.reflect.Proxy;

import de.afrouper.beans.Bean;
import de.afrouper.beans.BeanList;

public final class BeanFactory {

	private BeanFactory() {
	}

	@SuppressWarnings("unchecked")
	public static <B extends Bean> B createBean(Class<B> clazz) {
		BeanDescription description = BeanScanner.get().getBeanDescription(clazz);
		return (B) Proxy.newProxyInstance(getClassloader(), new Class[] { clazz },
				new BeanInvocationHandler(description));
	}

	@SuppressWarnings("unchecked")
	public static <E extends Bean> BeanList<E> createBeanList(Class<E> elementType) {
		BeanDescription description = BeanScanner.get().getBeanDescription(elementType);
		return (BeanList<E>) Proxy.newProxyInstance(getClassloader(), new Class[] { BeanList.class },
				new BeanListInvocationHandler<E>(description, elementType));
	}

	private static ClassLoader getClassloader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
