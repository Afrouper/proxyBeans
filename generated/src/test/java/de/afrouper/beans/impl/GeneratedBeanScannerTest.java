package de.afrouper.beans.impl;

import org.junit.Assert;
import org.junit.Test;

public class GeneratedBeanScannerTest {

	@Test
	public void scanPersonBean() {
		GeneratedBeanDescription description = GeneratedBeanScanner.get().getBeanDescription(Person.class);
		Assert.assertNotNull(description);
	}

	@Test
	public void simpleGetSet() {
		Person person = GeneratedBeanFactory.create(Person.class);
		Assert.assertNotNull(person);
		person.setName("Foo");
		Assert.assertEquals("Foo", person.getName());

		Person partner = GeneratedBeanFactory.create(Person.class);
		partner.setName("Bar");
		person.setPartner(partner);

		Assert.assertNotNull(person.getPartner());
		Assert.assertEquals("Bar", person.getPartner().getName());
	}

	@Test
	public void equal() {
		Person person1 = GeneratedBeanFactory.create(Person.class);
		Person person2 = GeneratedBeanFactory.create(Person.class);
		Assert.assertEquals(person1, person2);
		Assert.assertEquals(person1.hashCode(), person2.hashCode());

		person1.setName("Test");
		Assert.assertNotEquals(person1, person2);
		Assert.assertNotEquals(person1.hashCode(), person2.hashCode());

		person2.setName("Test");
		Assert.assertEquals(person1, person2);
		Assert.assertEquals(person1.hashCode(), person2.hashCode());
	}
}
