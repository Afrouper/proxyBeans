package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import de.afrouper.beans.api.ext.BeanAccess;
import de.afrouper.beans.api.ext.PropertyInformation;
import de.afrouper.beans.api.ext.SimpleBeanVisitor;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BeanVisitTest {

	@Test
	public void changedProperties() {
		Person person = BeanFactory.createBean(Person.class);
		person.setName("Lutz");

		BeanAccess<Person> beanAccess = BeanFactory.getBeanAccess(person);
		assertNotNull(beanAccess);
		SimpleBeanVisitor visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, true);

		Map<String, PropertyInformation> map = visitor.getInformationMap();
		assertEquals(1, map.size());
		PropertyInformation propertyInformation = map.get("name");
		assertNotNull(propertyInformation);
		assertEquals("name", propertyInformation.getName());
		assertEquals(String.class, propertyInformation.getType());

		beanAccess.resetTrackedChanges();
		visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, true);
		assertEquals(0, visitor.getInformationMap().size());
	}

	@Test
	public void allProps() {
		Person person = BeanFactory.createBean(Person.class);
		person.setName("Lutz");
		person.setPartner(BeanFactory.createBean(Person.class));
		person.getPartner().setName("Helga");

		BeanAccess<Person> beanAccess = BeanFactory.getBeanAccess(person);
		assertNotNull(beanAccess);
		SimpleBeanVisitor visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, false);

		assertEquals(2, visitor.getInformationMap().size());
		assertEquals("Lutz", visitor.getInformationMap().get("name").getValue());
		assertEquals("Helga", visitor.getInformationMap().get("partner.name").getValue());

		beanAccess.resetTrackedChanges();
		visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, false);
		assertEquals(2, visitor.getInformationMap().size());
	}

	@Test
	public void listElements() {
		Person person = BeanFactory.createBean(Person.class);
		Person c1 = BeanFactory.createBean(Person.class);
		Person c2 = BeanFactory.createBean(Person.class);
		c1.setName("Luke");
		c2.setName("Leia");
		person.setChilds(BeanFactory.createBeanList(Person.class, c1, c2));

		BeanAccess<Person> beanAccess = BeanFactory.getBeanAccess(person);
		SimpleBeanVisitor visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, false);

		assertEquals(2, visitor.getInformationMap().size());
		assertEquals("Luke", visitor.getInformationMap().get("childs[0].name").getValue());
		assertEquals("Leia", visitor.getInformationMap().get("childs[1].name").getValue());
	}
}
