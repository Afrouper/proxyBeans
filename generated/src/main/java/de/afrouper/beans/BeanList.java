package de.afrouper.beans;

import java.util.List;

public interface BeanList<B extends Bean> extends List<B>, Iterable<B> {

}
