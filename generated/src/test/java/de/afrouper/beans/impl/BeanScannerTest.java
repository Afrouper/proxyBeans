package de.afrouper.beans.impl;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;

public class BeanScannerTest {

	@Test
	public void scanPersonBean() {
		BeanDescription description = BeanScanner.get().getBeanDescription(Person.class);
		Assert.assertNotNull(description);

		Assert.assertFalse(description.isTrackChanges());
		Assert.assertEquals(Person.class, description.getBeanClass());
		Assert.assertEquals("Person", description.getName());

		BeanProperty property = description.getProperty("name");
		Assert.assertNotNull(property);
		Assert.assertEquals(String.class, property.getType());
		Assert.assertEquals("getName", property.getReadMethodName());
		Assert.assertEquals("setName", property.getWriteMethodName());
		Assert.assertArrayEquals(new Annotation[0], property.getAnnotations());
	}
}
