package de.afrouper.beans.impl;

import de.afrouper.beans.api.Bean;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

final class BeanUtil {

	private static final Set<Class<?>> primitiveTypes = new HashSet<>();

	static {
		primitiveTypes.add(short.class);
		primitiveTypes.add(int.class);
		primitiveTypes.add(long.class);
		primitiveTypes.add(float.class);
		primitiveTypes.add(double.class);
		primitiveTypes.add(char.class);
		primitiveTypes.add(boolean.class);

		primitiveTypes.add(BigInteger.class);
		primitiveTypes.add(BigDecimal.class);
		primitiveTypes.add(String.class);

		primitiveTypes.add(Short.class);
		primitiveTypes.add(Integer.class);
		primitiveTypes.add(Long.class);
		primitiveTypes.add(Float.class);
		primitiveTypes.add(Double.class);
		primitiveTypes.add(Character.class);
		primitiveTypes.add(Boolean.class);
	}

	private BeanUtil() {
	}

	static BeanImpl getBeanImpl(Object obj) {
		if(obj == null) {
			return null;
		}
		if(BeanImpl.class.isInstance(obj)) {
			return (BeanImpl) obj;
		}
		if(Bean.class.isInstance(obj) && Proxy.isProxyClass(obj.getClass())) {
			return (BeanImpl) Proxy.getInvocationHandler(obj);
		}
		return null;
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

	static boolean isPrimitiveType(Class<?> typeClass) {
		return primitiveTypes.contains(typeClass);
	}
}
