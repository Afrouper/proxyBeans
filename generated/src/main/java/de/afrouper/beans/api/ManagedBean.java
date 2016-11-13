package de.afrouper.beans.api;

/**
 * Extends the {@link Bean} interface for adding special operations to the generated bean instances
 */
public interface ManagedBean extends Bean{

	/**
	 * Utility to see if any data has changed
	 * @return <code>true</code> if data was changed
	 */
	boolean isDataChanged();

	/**
	 * Resets the data changed flag.
	 */
	void resetDataChanged();
}
