package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import de.afrouper.beans.api.BeanSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class BeanSetTest {

	private static final int MAX = 5;

	@Test
	public void simpleSet() {
		BeanSet<Person> persons = createPersonsSet();
		Assert.assertEquals(MAX, persons.size());

		Iterator<Person> iterator = persons.iterator();
		for (int i = 0; i < MAX; ++i) {
			Person person = iterator.next();
			Assert.assertTrue(persons.contains(person));
		}
	}

	@Test
	public void add() {
		Person p1 = BeanFactory.createBean(Person.class);
		p1.setName("Karl");
		p1.setAge(42);

		Person p2 = BeanFactory.createBean(Person.class);
		p2.setName("Karl");
		p2.setAge(42);

		Person p3 = BeanFactory.createBean(Person.class);
		p3.setName("Ingo");
		p3.setAge(15);

		BeanSet<Person> persons = BeanFactory.createBeanSet(Person.class, p1, p2, p3);
		Assert.assertEquals(2, persons.size());

		Assert.assertFalse(persons.add(p2));
		Assert.assertFalse(persons.add(p3));
		Assert.assertEquals(2, persons.size());
	}

	@Test
	public void remove() {
		Person p1 = BeanFactory.createBean(Person.class);
		p1.setName("Karl");
		p1.setAge(42);

		Person p2 = BeanFactory.createBean(Person.class);
		p2.setName("Karl");
		p2.setAge(42);

		Person p3 = BeanFactory.createBean(Person.class);
		p3.setName("Ingo");
		p3.setAge(15);

		BeanSet<Person> persons = BeanFactory.createBeanSet(Person.class, p1, p2, p3);
		Assert.assertEquals(2, persons.size());

		Assert.assertTrue(persons.remove(p1));
		Assert.assertFalse(persons.remove(p2));
		Assert.assertTrue(persons.remove(p3));
		Assert.assertEquals(0, persons.size());
	}

	private BeanSet<Person> createPersonsSet() {
		BeanSet<Person> persons = BeanFactory.createBeanSet(Person.class);
		Assert.assertNotNull(persons);

		for (int i = 0; i < MAX; ++i) {
			Person person = BeanFactory.createBean(Person.class);
			person.setAge(i);
			person.setName("Name_" + (i + 1));
			persons.add(person);
		}
		return persons;
	}
}
