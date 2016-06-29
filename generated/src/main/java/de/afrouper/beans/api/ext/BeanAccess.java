package de.afrouper.beans.api.ext;

public interface BeanAccess {

	void visit(BeanVisitor visitor, boolean onlyChangedProperties);

	void resetTrackedChanges();
}
