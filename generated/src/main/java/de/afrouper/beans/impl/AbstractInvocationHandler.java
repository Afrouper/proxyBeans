package de.afrouper.beans.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

abstract class AbstractInvocationHandler implements InvocationHandler {

	@Override
	public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		switch (methodName) {
		case "hashCode":
			return hashCode();
		case "equals":
			return equals(args[0]);
		case "toString":
			return toString();
		default:
			return invokeMethod(proxy, method, args);
		}
	}

	protected abstract Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable;
}
