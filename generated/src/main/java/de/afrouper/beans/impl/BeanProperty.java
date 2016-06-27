package de.afrouper.beans.impl;

import java.io.Serializable;

public final class BeanProperty implements Serializable {

	private static final long serialVersionUID = 2667698194085520045L;

	private final String name;

	private String readMethodName;

	private String writeMethodName;

	public BeanProperty(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "BeanProperty [name=" + name + "]";
	}

	public void setReadMethodName(String readMethodName) {
		this.readMethodName = readMethodName;
	}

	public String getReadMethodName() {
		return readMethodName;
	}

	public void setWriteMethodName(String writeMethodName) {
		this.writeMethodName = writeMethodName;
	}

	public String getWriteMethodName() {
		return writeMethodName;
	}
}
