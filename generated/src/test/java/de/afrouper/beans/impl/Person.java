package de.afrouper.beans.impl;

import de.afrouper.beans.GeneratedBean;

public interface Person extends GeneratedBean {

	String getName();

	void setName(String name);

	Integer getAge();

	void setHight(double height);

	Person getPartner();

	void setPartner(Person person);
}
