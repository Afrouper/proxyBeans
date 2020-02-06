package de.afrouper.beans.json;

import com.google.gson.stream.JsonWriter;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.ext.BeanVisitor;

import java.io.IOException;
import java.lang.annotation.Annotation;

class GsonVisitor implements BeanVisitor {

    private final JsonWriter writer;

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
                writer.name(name).value((char) value);
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
    }

    @Override
    public void beanEnd(String name) {

    }

    @Override
    public void listStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations) {

    }

    @Override
    public void listIndex(int index) {

    }

    @Override
    public void listEnd(String name) {

    }

    @Override
    public void setStart(String name, Class<? extends Bean> elementClass, Annotation[] annotations) {

    }

    @Override
    public void setEnd(String name) {

    }
}
