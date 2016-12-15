package de.afrouper.beans.api.ext;

import de.afrouper.beans.api.Bean;

public interface BeanAccess<B extends Bean> {

	void visit(BeanVisitor visitor, boolean onlyChangedProperties);

	void resetTrackedChanges();
}
