package de.afrouper.beans.api;

/**
 *
 */
public interface BeanFactoryDelegate {

    <B extends Bean> B createBean(Class<B> clazz);

    <B extends Bean> BeanList<B> createBeanList(Class<B> elementType);
}
