package de.afrouper.beans.impl;

import java.io.Serializable;

@FunctionalInterface
public interface BeanListener extends Serializable {

    void valueChanged();
}
