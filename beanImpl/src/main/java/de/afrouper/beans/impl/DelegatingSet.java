package de.afrouper.beans.impl;


import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanSet;
import de.afrouper.beans.api.ext.BeanVisitor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

final class DelegatingSet<E extends Bean> implements BeanSet<E>, BeanImpl<E>, Serializable {

    private final HashSet<E> set;

    private final Class<E> elementType;

    DelegatingSet(Class<E> elementType, BeanDescription description) {
        this.elementType = elementType;
        set = new HashSet<>();
    }

    private void elementAdded(E element) {

    }

    private void elementRemoved(Object element) {

    }

    private void allElementsRemoved() {

    }

    Class<E> getElementType() {
        return elementType;
    }

    @Override
    public void visit(BeanVisitor visitor, boolean onlyChangedProperties) {
		//TODO: Implement change tracking
		int index = 0;
		Iterator<?> iter = set.iterator();
		while(iter.hasNext()) {
			index++;
			BeanImpl beanImpl = BeanUtil.getBeanImpl(iter.next());
			visitor.beanStartInArray(index, getElementType(), null);
			beanImpl.visit(visitor, onlyChangedProperties);
			visitor.beanEndInArray(index);
		}
	}

    @Override
    public void resetTrackedChanges() {

    }

	@Override
	public void addBeanListener(BeanListener listener) {

	}

	@Override
	public void removeBeanListener(BeanListener listener) {

	}

	//------------ Set Methods

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return new DelegatingSetIterator(set.iterator());
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(E e) {
    	boolean added = set.add(e);
    	if(added) {
    		elementAdded(e);
		}
		return added;
	}

	@Override
	public boolean remove(Object o) {
    	boolean removed = set.remove(o);
    	if(removed) {
    		elementRemoved(o);
		}
		return removed;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
    	boolean added = false;
    	for(E e : c) {
    		added &= add(e);
		}
		return added;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
    	//Callback method "remove" will be called by Iterators remove method.
		return set.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean removed = false;
		for(Object o : c) {
			removed &= remove(o);
		}
		return removed;
	}

	@Override
	public void clear() {
		allElementsRemoved();
		set.clear();
	}

	private class DelegatingSetIterator implements Iterator<E> {

		private Iterator<E> iter;

		DelegatingSetIterator(Iterator<E> iter) {
			this.iter = iter;
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public E next() {
			return iter.next();
		}

		@Override
		public void remove() {
			E next = next();
			DelegatingSet.this.remove(next);
		}
	}
}
