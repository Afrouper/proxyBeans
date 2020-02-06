package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BeanTest {

	@Test
	public void simpleGetSet() {
		Person person = BeanFactory.createBean(Person.class);
		assertNotNull(person);
		person.setName("Foo");
		assertEquals("Foo", person.getName());

		Person partner = BeanFactory.createBean(Person.class);
		partner.setName("Bar");
		person.setPartner(partner);

		assertNotNull(person.getPartner());
		assertEquals("Bar", person.getPartner().getName());
	}

	@Test
	public void childBean() {
		Person person = BeanFactory.createBean(Person.class);
		Person partner = BeanFactory.createBean(Person.class);
		partner.setName("Foo");
		person.setPartner(partner);
		person.getPartner().setAge(58);

		assertEquals("Foo", person.getPartner().getName());
		assertNotEquals(person.getName(), person.getPartner().getName());
		assertEquals(58, person.getPartner().getAge().intValue());
		assertNotEquals(person.getAge(), person.getPartner().getAge());
		assertEquals(partner.getName(), person.getPartner().getName());
	}

	@Test
	public void defaultMethod() {
		Person person = BeanFactory.createBean(Person.class);
		person.setName("Lutz");
		person.setAge(42);
		assertEquals("Lutz is 42", person.nameAge());
	}
}
