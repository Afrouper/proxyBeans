package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EqualityTest {

	@Test
	public void depth1() {
		Person person1 = BeanFactory.createBean(Person.class);
		Person person2 = BeanFactory.createBean(Person.class);
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());

		person1.setName("Test");
		assertNotEquals(person1, person2);
		assertNotEquals(person1.hashCode(), person2.hashCode());

		person2.setName("Test");
		assertEquals(person1, person2);
		assertEquals(person1.hashCode(), person2.hashCode());
	}
}
