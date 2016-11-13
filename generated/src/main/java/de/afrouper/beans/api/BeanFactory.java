package de.afrouper.beans.api;

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
        if(!iterator.hasNext()) {
            throw new IllegalStateException("No implementation of " + BeanFactoryDelegate.class.getName() + " found. Check Classpath/configuration.");
        }
        BeanFactoryDelegate beanFactoryDelegate = iterator.next();
        if(iterator.hasNext()) {
            throw new IllegalStateException("Only one Implementation of " + BeanFactoryDelegate.class.getName() + " can be used!");
        }
        return beanFactoryDelegate;
    }

    /**
     * Creates a {@link Bean} instance
     * @param clazz {@link Class} defining the bean
     * @return Instance of the given interface class
     */
    public static <B extends Bean> B createBean(Class<B> clazz) {
        return delegate.createBean(clazz);
    }

    /**
     * Creates a new instance of a {@link BeanList}
     * @param elementType Type of objects which can be stored in the list
     * @return Instance of the list
     */
    public static <B extends Bean> BeanList<B> createBeanList(Class<B> elementType) {
        return delegate.createBeanList(elementType);
    }
}
