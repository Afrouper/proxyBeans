package de.afrouper.beans.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Disables the change tracking of properties or {@link Bean}s.
 *
 * @see ManagedBean
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface NoChangeTracking {

}
