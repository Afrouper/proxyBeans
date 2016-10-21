package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class BeanTest {

	@Test
	public void simpleGetSet() {
		Person person = BeanFactory.createBean(Person.class);
		Assert.assertNotNull(person);
		person.setName("Foo");
		Assert.assertEquals("Foo", person.getName());

		Person partner = BeanFactory.createBean(Person.class);
		partner.setName("Bar");
		person.setPartner(partner);

		Assert.assertNotNull(person.getPartner());
		Assert.assertEquals("Bar", person.getPartner().getName());
	}

	@Test
	public void childBean() {
		Person person = BeanFactory.createBean(Person.class);
		Person partner = BeanFactory.createBean(Person.class);
		partner.setName("Foo");
		person.setPartner(partner);
		person.getPartner().setAge(58);

		Assert.assertEquals("Foo", person.getPartner().getName());
		Assert.assertNotEquals(person.getName(), person.getPartner().getName());
		Assert.assertEquals(58, person.getPartner().getAge().intValue());
		Assert.assertNotEquals(person.getAge(), person.getPartner().getAge());
		Assert.assertEquals(partner.getName(), person.getPartner().getName());

	}
}
