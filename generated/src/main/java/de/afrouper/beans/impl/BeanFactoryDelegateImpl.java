package de.afrouper.beans.impl;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanFactoryDelegate;
import de.afrouper.beans.api.BeanList;
import de.afrouper.beans.api.BeanSet;

import java.lang.reflect.Proxy;

public final class BeanFactoryDelegateImpl implements BeanFactoryDelegate {

	public BeanFactoryDelegateImpl() {
	}

	@SuppressWarnings("unchecked")
	public <B extends Bean> B createBean(Class<B> clazz) {
		BeanDescription description = BeanScanner.get().getBeanDescription(clazz);
		return (B) Proxy.newProxyInstance(getClassloader(), new Class[] { clazz },
				new BeanInvocationHandler(description));
	}

	public <E extends Bean> BeanList<E> createBeanList(Class<E> elementType) {
		BeanDescription description = BeanScanner.get().getBeanDescription(elementType);
		return new DelegatingList<>(elementType, description);
	}

	@Override
	public <B extends Bean> BeanSet<B> createBeanSet(Class<B> elementType) {
		BeanDescription description = BeanScanner.get().getBeanDescription(elementType);
		return new DelegatingSet<>(elementType, description);
	}

	private static ClassLoader getClassloader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
