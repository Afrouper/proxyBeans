package de.afrouper.beans.impl;


import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanList;

import java.io.Serializable;
import java.util.*;

final class DelegatingList<E extends Bean> implements BeanList<E>, Serializable {

    private final ArrayList<E> list;

    private final Class<E> elementType;

    private final BeanDescription description;

    DelegatingList(Class<E> elementType, BeanDescription description) {
        this.elementType = elementType;
        this.description = description;
        list = new ArrayList<>();
    }

    private void elementAdded(E element, int index) {

    }

    private void elementSeted(E element, int index) {

    }

    private void elementRemoved(E element, int index) {

    }

    private void allElementsRemoved() {

    }

    //------------ List Methods

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new DelegatingListIterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        boolean added = list.add(e);
        if(added) {
            elementAdded(e, list.size() - 1);
        }
        return added;
    }

    @Override
    public boolean remove(Object o) {
        int index = list.indexOf(o);
        if(index >= 0) {
            list.remove(index);
            elementRemoved((E) o, index);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean added = false;
        for (E e : c) {
            added |= add(e);
        }
        return added;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        //TODO: Check for list-change
        boolean added = true;
        for (E e : c) {
            add(index++, e);
        }
        return added;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (Object o : c) {
            removed |= remove(o);
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation retainAll currently not supported!");
    }

    @Override
    public void clear() {
        list.clear();
        allElementsRemoved();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = list.set(index, element);
        elementSeted(element, index);
        return old;
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        elementAdded(element, index);
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        if(removed != null) {
            elementRemoved(removed, index);
        }
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Operation listIterator currently not supported.");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Operation listIterator(int index) currently not supported.");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Operation subList currently not supported.");
    }

    private class DelegatingListIterator implements Iterator<E> {

        private int index;

        @Override
        public boolean hasNext() {
            return index <= size();
        }

        @Override
        public E next() {
            index++;
            return get(index);
        }

        @Override
        public void remove() {
            DelegatingList.this.remove(index);
        }
    }
}
