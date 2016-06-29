package de.afrouper.beans.impl;

import java.beans.Introspector;
import java.lang.reflect.Method;

public final class JavaBeanUtil {

	private JavaBeanUtil() {
	}

	public static boolean isGetterMethod(Method method) {
		if (method == null) {
			return false;
		} else {
			return isGetterMethod(method.getName());
		}
	}

	public static boolean isSetterMethod(Method method) {
		if (method == null) {
			return false;
		} else {
			return isSetterMethod(method.getName());
		}
	}

	public static boolean isSetterMethod(String name) {
		if (name == null) {
			return false;
		} else {
			return name.startsWith("set");
		}
	}

	public static boolean isGetterMethod(String name) {
		if (name == null) {
			return false;
		} else {
			return name.startsWith("get") || name.startsWith("is");
		}
	}

	public static String getPropertyNameFromMethodName(String methodName) {
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