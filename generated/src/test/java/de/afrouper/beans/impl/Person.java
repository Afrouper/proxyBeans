package de.afrouper.beans.impl;

import de.afrouper.beans.Bean;
import de.afrouper.beans.BeanList;

public interface Person extends Bean {

	String getName();

	void setName(String name);

	Integer getAge();

	void setAge(Integer age);

	void setHight(double height);

	Person getPartner();

	void setPartner(Person person);

	BeanList<Person> getChilds();

	void setChilds(BeanList<Person> childs);
}
