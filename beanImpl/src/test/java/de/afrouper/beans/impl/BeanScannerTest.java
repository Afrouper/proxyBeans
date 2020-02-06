package de.afrouper.beans.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;

public class BeanScannerTest {

	@Test
	public void scanPersonBean() {
		BeanDescription description = BeanScanner.get().getBeanDescription(Person.class);
		assertNotNull(description);

		assertFalse(description.isTrackChanges());
		assertEquals(Person.class, description.getBeanClass());
		assertEquals("Person", description.getName());

		BeanProperty property = description.getProperty("name");
		assertNotNull(property);
		assertEquals(String.class, property.getType());
		assertEquals("getName", property.getReadMethodName());
		assertEquals("setName", property.getWriteMethodName());
		assertArrayEquals(new Annotation[0], property.getAnnotations());
	}
}
