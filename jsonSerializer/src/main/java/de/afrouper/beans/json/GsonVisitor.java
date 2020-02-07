package de.afrouper.beans.json;

import com.google.gson.stream.JsonWriter;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.ext.BeanVisitor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Stack;

class GsonVisitor implements BeanVisitor {

    private final JsonWriter writer;

    private final Stack<BeanStackElement> beanStack = new Stack<>();

    public GsonVisitor(JsonWriter writer) {
        this.writer = writer;
    }

    @Override
    public void property(String name, Object value, Class<?> type, Annotation[] annotations) {
        try {
            if(Number.class.isAssignableFrom(type)) {
                writer.name(name).value((Number) value);
            }
            else if(String.class.isAssignableFrom(type)) {
                writer.name(name).value(value.toString());
            }
            else if(Boolean.class.isAssignableFrom(type)) {
                writer.name(name).value((Boolean) value);
            }
            else if(boolean.class.isAssignableFrom(type)) {
                writer.name(name).value((boolean) value);
            }
            else if(int.class.isAssignableFrom(type)) {
                writer.name(name).value((int) value);
            }
            else if(long.class.isAssignableFrom(type)) {
                writer.name(name).value((long) value);
            }
            else if(double.class.isAssignableFrom(type)) {
                writer.name(name).value((double) value);
            }
            else if(float.class.isAssignableFrom(type)) {
                writer.name(name).value((float) value);
            }
            else if(char.class.isAssignableFrom(type)) {
                writer.name(name).value(Character.toString((Character) value));
            }
            else if(Character.class.isAssignableFrom(type)) {
                writer.name(name).value((Character) value);
            }
            else {
                throw new IllegalArgumentException("Illegal type " + type.getName() + " for Json serialization.");
            }
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void beanStart(String name, Class<? extends Bean> beanClass, Annotation[] annotations) {
        try {
            beanStack.push(new BeanStackElement(name, beanClass, Type.OBJECT, annotations));
            writer.name(name).beginObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void beanEnd(String name) {
        try {
            BeanStackElement element = beanStack.pop();
            if(!Type.OBJECT.equals(element.getType()) && !name.equals(element.getName())) {
                throw new IllegalArgumentException("Invalid Beanstack. Expected bean " + element.getName() + ", got " + name);
            }
            writer.endObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void listStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations) {
        try {
            beanStack.push(new BeanStackElement(name, elementClass, Type.LIST, annotations));
            writer.name(name).beginArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void beanStartInArray(int index, Class<? extends Bean> beanClass, Annotation[] annotations) {
        try {
            beanStack.push(new BeanStackElement(index, beanClass, Type.LIST_OBJECT, annotations));
            writer.beginObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void beanEndInArray(int index) {
        try {
            BeanStackElement stackElement = beanStack.pop();
            if(!Type.LIST_OBJECT.equals(stackElement.getType()) && index != stackElement.getIndex()) {
                throw new IllegalArgumentException("Invalid Beanstack. Expected list object for index " + stackElement.getIndex() + ", got " + index);
            }
            writer.endObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void listEnd(String name) {
        try {
            BeanStackElement element = beanStack.pop();
            if(!name.equals(element.getName())) {
                throw new IllegalArgumentException("Invalid Beanstack. Expected list " + element.getName() + ", got " + name);
            }
            writer.endArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void setStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations) {

    }

    @Override
    public void setEnd(String name) {

    }

    private class BeanStackElement{
        private String name;
        private int index;
        private final Class<? extends Bean> beanClass;
        private final Annotation[] annotations;
        private final Type type;

        private BeanStackElement(Class<? extends Bean> beanClass, Type type, Annotation[] annotations) {
            this.beanClass = beanClass;
            this.type = type;
            this.annotations = annotations;
        }

        public BeanStackElement(String name, Class<? extends Bean> beanClass, Type type, Annotation[] annotations) {
            this(beanClass, type, annotations);
            this.name = name;
        }

        public BeanStackElement(int index, Class<? extends Bean> beanClass, Type type, Annotation[] annotations) {
            this(beanClass, type, annotations);
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public Type getType() {
            return type;
        }
    }

    private enum Type {
        OBJECT, LIST, LIST_OBJECT, SET;
    }
}
