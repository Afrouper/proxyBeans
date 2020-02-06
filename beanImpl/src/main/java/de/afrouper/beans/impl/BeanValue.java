package de.afrouper.beans.impl;

import java.io.Serializable;

final class BeanValue implements Serializable {

	private static final long serialVersionUID = 1330614267633009600L;

	private Object value;

	private BeanListener listener;

	BeanValue(Object value) {
		this.value = value;
	}

	Object getValue() {
		return value;
	}

	void setValue(Object value) {
		this.value = value;
	}

	void setListener(BeanListener listener) {
		this.listener = listener;
	}

	BeanListener getListener() {
		return listener;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BeanValue other = (BeanValue) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return value != null ? value.toString() : "null";
	}
}
