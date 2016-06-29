package de.afrouper.beans.impl;

import org.junit.Assert;
import org.junit.Test;

import de.afrouper.beans.BeanList;

public class BeanListTest {

	@Test
	public void simpleList() {
		BeanList<Person> persons = BeanFactory.createBeanList(Person.class);
		Assert.assertNotNull(persons);

		final int MAX = 5;
		for (int i = 0; i < MAX; ++i) {
			Person person = BeanFactory.createBean(Person.class);
			person.setAge(i);
			person.setName("Name_" + (i + 1));
			persons.add(person);
		}
		Assert.assertEquals(MAX, persons.size());

		for (int i = 0; i < MAX; ++i) {
			Person person = persons.get(i);
			Assert.assertEquals(i, person.getAge().intValue());
			Assert.assertEquals("Name_" + (i+1), person.getName());
		}
	}
}
