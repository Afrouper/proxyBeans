package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class EqualityTest {

	@Test
	public void depth1() {
		Person person1 = BeanFactory.createBean(Person.class);
		Person person2 = BeanFactory.createBean(Person.class);
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
