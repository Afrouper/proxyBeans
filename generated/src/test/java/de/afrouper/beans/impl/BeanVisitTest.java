package de.afrouper.beans.impl;

import de.afrouper.beans.api.BeanFactory;
import de.afrouper.beans.api.ext.BeanAccess;
import de.afrouper.beans.api.ext.PropertyInformation;
import de.afrouper.beans.api.ext.SimpleBeanVisitor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class BeanVisitTest {

	@Test
	public void changedProperties() {
		Person person = BeanFactory.createBean(Person.class);
		person.setName("Lutz");

		BeanAccess<Person> beanAccess = BeanFactory.getBeanAccess(person);
		Assert.assertNotNull(beanAccess);
		SimpleBeanVisitor visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, true);

		Map<String, PropertyInformation> map = visitor.getInformationMap();
		Assert.assertEquals(1, map.size());
		PropertyInformation propertyInformation = map.get("name");
		Assert.assertNotNull(propertyInformation);
		Assert.assertEquals("name", propertyInformation.getName());
		Assert.assertEquals(String.class, propertyInformation.getType());

		beanAccess.resetTrackedChanges();
		visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, true);
		Assert.assertEquals(0, visitor.getInformationMap().size());
	}

	@Test
	public void allProps() {
		Person person = BeanFactory.createBean(Person.class);
		person.setName("Lutz");
		person.setPartner(BeanFactory.createBean(Person.class));
		person.getPartner().setName("Helga");

		BeanAccess<Person> beanAccess = BeanFactory.getBeanAccess(person);
		Assert.assertNotNull(beanAccess);
		SimpleBeanVisitor visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, false);

		Assert.assertEquals(2, visitor.getInformationMap().size());
		Assert.assertEquals("Lutz", visitor.getInformationMap().get("name").getValue());
		Assert.assertEquals("Helga", visitor.getInformationMap().get("partner.name").getValue());

		beanAccess.resetTrackedChanges();
		visitor = new SimpleBeanVisitor();
		beanAccess.visit(visitor, false);
		Assert.assertEquals(2, visitor.getInformationMap().size());
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

		Assert.assertEquals(2, visitor.getInformationMap().size());
		Assert.assertEquals("Luke", visitor.getInformationMap().get("childs[0].name").getValue());
		Assert.assertEquals("Leia", visitor.getInformationMap().get("childs[1].name").getValue());
	}
}
