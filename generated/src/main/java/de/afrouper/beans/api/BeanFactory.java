package de.afrouper.beans.api;

import de.afrouper.beans.api.ext.BeanAccess;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Factory for creating instances of {@link Bean}s
 */
public final class BeanFactory {

	private static final BeanFactoryDelegate delegate;

	static {
		delegate = createDelegate();
	}

	private static BeanFactoryDelegate createDelegate() {
		ServiceLoader<BeanFactoryDelegate> beanFactoryDelegates = ServiceLoader.load(BeanFactoryDelegate.class);
		Iterator<BeanFactoryDelegate> iterator = beanFactoryDelegates.iterator();
		if (!iterator.hasNext()) {
			throw new IllegalStateException("No implementation of " + BeanFactoryDelegate.class.getName() + " found. Check Classpath/configuration.");
		}
		BeanFactoryDelegate beanFactoryDelegate = iterator.next();
		if (iterator.hasNext()) {
			throw new IllegalStateException("Only one Implementation of " + BeanFactoryDelegate.class.getName() + " can be used!");
		}
		return beanFactoryDelegate;
	}

	public static <B extends Bean> BeanAccess<B> getBeanAccess(B bean) {
		if (bean != null && Proxy.isProxyClass(bean.getClass())) {
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(bean);
			if (BeanAccess.class.isInstance(invocationHandler)) {
				return (BeanAccess<B>) invocationHandler;
			}
		}
		return null;
	}

	/**
	 * Creates a {@link Bean} instance
	 *
	 * @param clazz {@link Class} defining the bean
	 * @return Instance of the given interface class
	 */
	public static <B extends Bean> B createBean(Class<B> clazz) {
		return delegate.createBean(clazz);
	}

	/**
	 * Creates a new instance of a {@link BeanList}
	 *
	 * @param elementType Type of objects which can be stored in the list
	 * @return Instance of the list
	 */
	public static <B extends Bean> BeanList<B> createBeanList(Class<B> elementType, B... elements) {
		BeanList<B> list = delegate.createBeanList(elementType);
		for (B element : elements) {
			list.add(element);
		}
		return list;
	}
}
