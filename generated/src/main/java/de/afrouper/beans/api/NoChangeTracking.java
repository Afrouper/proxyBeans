package de.afrouper.beans.api;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Disables the change tracking of properties or {@link Bean}s.
 *
 * @see de.afrouper.beans.api.ext.BeanAccess
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface NoChangeTracking {

}
