package de.afrouper.beans.api;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 *
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

    public static <B extends Bean> B createBean(Class<B> clazz) {
        return delegate.createBean(clazz);
    }

    public static <B extends Bean> BeanList<B> createBeanList(Class<B> elementType) {
        return delegate.createBeanList(elementType);
    }
}
