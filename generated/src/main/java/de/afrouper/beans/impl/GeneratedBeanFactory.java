package de.afrouper.beans.impl;

import java.lang.reflect.Proxy;

import de.afrouper.beans.GeneratedBean;

public final class GeneratedBeanFactory {

	private GeneratedBeanFactory() {
	}

	@SuppressWarnings("unchecked")
	public static <B extends GeneratedBean> B create(Class<B> clazz) {
		GeneratedBeanDescription description = GeneratedBeanScanner.get().getBeanDescription(clazz);
		return (B) Proxy.newProxyInstance(getClassloader(), new Class[] { clazz },
				new GeneratedBeanInvocationHandler(description));
	}

	private static ClassLoader getClassloader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
