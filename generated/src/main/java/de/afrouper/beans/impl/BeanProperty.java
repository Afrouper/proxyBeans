package de.afrouper.beans.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;

final class BeanProperty implements Serializable {

	private static final long serialVersionUID = 2667698194085520045L;

	private final String name;

	private String readMethodName;

	private String writeMethodName;

	private Annotation[] annotations;

	private boolean trackChanges;

	BeanProperty(String name, boolean trackChanges) {
		this.name = name;
		this.trackChanges = trackChanges;
	}

	String getName() {
		return name;
	}

	boolean isTrackChanges() {
		return trackChanges;
	}

	@Override
	public String toString() {
		return "BeanProperty '" + name + "'";
	}

	void setReadMethodName(String readMethodName) {
		this.readMethodName = readMethodName;
	}

	String getReadMethodName() {
		return readMethodName;
	}

	void setWriteMethodName(String writeMethodName) {
		this.writeMethodName = writeMethodName;
	}

	String getWriteMethodName() {
		return writeMethodName;
	}

	void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	Annotation[] getAnnotations() {
		return annotations;
	}
}
