package de.afrouper.beans.impl;

import org.junit.Assert;
import org.junit.Test;

public class BeanScannerTest {

	@Test
	public void scanPersonBean() {
		BeanDescription description = BeanScanner.get().getBeanDescription(Person.class);
		Assert.assertNotNull(description);
	}
}
