package de.afrouper.beans.api;

import java.util.List;

/**
 * Base interface for {@link List} instances in a {@link Bean}
 * @param <B> Type of objects in the {@link List}
 */
public interface BeanList<B extends Bean> extends List<B>, Iterable<B> {

}
