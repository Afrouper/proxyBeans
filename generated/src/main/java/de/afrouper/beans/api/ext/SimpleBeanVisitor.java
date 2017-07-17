package de.afrouper.beans.api.ext;

import de.afrouper.beans.api.Bean;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleBeanVisitor implements BeanVisitor {

	private Map<String, PropertyInformation> informationMap = new HashMap<>();

	private Stack<StackElement> path = new Stack<>();

	public Map<String, PropertyInformation> getInformationMap() {
		return Collections.unmodifiableMap(informationMap);
	}

	@Override
	public void property(String name, Object value, Class<?> type, Annotation[] annotations) {
		informationMap.put(createName(name), new PropertyInformation(name, value, type, annotations));
	}

	@Override
	public void beanStart(String name, Class<? extends Bean> beanClass, Annotation[] annotations) {
		path.push(new StackElement(name, false));
	}

	@Override
	public void beanEnd(String name) {
		if (!path.peek().getPath().equals(name)) {
			throw new IllegalStateException(("Stack corrupt. Expected " + path.peek() + " got " + name));
		}
		path.pop();
	}

	@Override
	public void listStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations) {
		path.push(new StackElement(name, true));
	}

	public void listIndex(int index) {
		if(path.isEmpty() || !path.peek().isList()) {
			throw new IllegalStateException("Stack corrupt. Listindex should be set an path is empty or no list element");
		}
		path.peek().setIndex(index);
	}

	@Override
	public void listEnd(String name) {
		if (!path.peek().getPath().equals(name)) {
			throw new IllegalStateException(("Stack corrupt. Expected " + path.peek() + " got " + name));
		}
		path.pop();
	}

	@Override
	public void setStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations) {

	}

	@Override
	public void setEnd(String name) {

	}

	private String createName(String leaf) {
		if (path.isEmpty()) {
			return leaf;
		} else {
			String currentPath = path.stream().map(StackElement::getPath).collect(Collectors.joining("."));
			if(path.peek().isList()) {
				currentPath = currentPath + "[" + path.peek().getIndex() + "]";
			}
			return currentPath + "." + leaf;
		}
	}

	private final class StackElement {
		private final String path;
		private final boolean list;
		private int index;

		StackElement(String path, boolean list) {
			this.path = path;
			this.list = list;
		}

		String getPath() {
			return path;
		}

		boolean isList() {
			return list;
		}

		int getIndex() {
			return index;
		}

		void setIndex(int index) {
			this.index = index;
		}
	}
}
