package de.afrouper.beans.impl;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.ext.BeanAccess;

interface BeanImpl<B extends Bean> extends BeanAccess<B> {

    void addBeanListener(BeanListener listener);

    void removeBeanListener(BeanListener listener);
}
