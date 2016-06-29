package de.afrouper.beans.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public final class BeanProperty implements Serializable {

	private static final long serialVersionUID = 2667698194085520045L;

	private final String name;

	private String readMethodName;

	private String writeMethodName;

	private Annotation[] annotations;

	private boolean trackChanges;

	public BeanProperty(String name, boolean trackChanges) {
		this.name = name;
		this.trackChanges = trackChanges;
	}

	public String getName() {
		return name;
	}

	public boolean isTrackChanges() {
		return trackChanges;
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

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}
}
