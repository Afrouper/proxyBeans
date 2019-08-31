package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;

import de.afrouper.beans.api.BeanList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BeanListTest {

	private static final int MAX = 5;

	@Test
	public void simpleList() {
		BeanList<Person> persons = createPersonsList();
		assertEquals(MAX, persons.size());

		for (int i = 0; i < MAX; ++i) {
			Person person = persons.get(i);
			assertEquals(i, person.getAge().intValue());
			assertEquals("Name_" + (i + 1), person.getName());
		}
	}

	@Test
	public void childLists() {
		Person person = BeanFactory.createBean(Person.class);
		person.setChilds(createPersonsList());

		assertNotNull(person.getChilds());
		assertEquals(MAX, person.getChilds().size());
	}

	private BeanList<Person> createPersonsList() {
		BeanList<Person> persons = BeanFactory.createBeanList(Person.class);
		assertNotNull(persons);

		for (int i = 0; i < MAX; ++i) {
			Person person = BeanFactory.createBean(Person.class);
			person.setAge(i);
			person.setName("Name_" + (i + 1));
			persons.add(person);
		}
		return persons;
	}
}
