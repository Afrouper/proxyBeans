package de.afrouper.beans.impl;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.ext.BeanAccess;

final class BeanUtil {

	private BeanUtil() {
	}

	static BeanAccess getBeanAccess(Object object) {
		if (object instanceof Bean) {
			return (BeanAccess) Proxy.getInvocationHandler(object);
		} else {
			return null;
		}
	}

	static boolean isGetterMethod(Method method) {
		if (method == null) {
			return false;
		} else {
			return isGetterMethod(method.getName());
		}
	}

	static boolean isSetterMethod(Method method) {
		if (method == null) {
			return false;
		} else {
			return isSetterMethod(method.getName());
		}
	}

	static boolean isSetterMethod(String name) {
		if (name == null) {
			return false;
		} else {
			return name.startsWith("set");
		}
	}

	static boolean isGetterMethod(String name) {
		if (name == null) {
			return false;
		} else {
			return name.startsWith("get") || name.startsWith("is");
		}
	}

	static String getPropertyNameFromMethodName(String methodName) {
		if (methodName == null) {
			return null;
		}
		if (methodName.startsWith("set")) {
			return Introspector.decapitalize(methodName.substring(3));
		}
		if (methodName.startsWith("get")) {
			return Introspector.decapitalize(methodName.substring(3));
		}
		if (methodName.startsWith("is")) {
			return Introspector.decapitalize(methodName.substring(2));
		}
		throw new IllegalArgumentException(
				"Methodname " + methodName + " is no valid bean method (Getter- or Settermethod)");
	}

}
