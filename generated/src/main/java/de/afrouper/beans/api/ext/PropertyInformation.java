package de.afrouper.beans.api.ext;

import java.lang.annotation.Annotation;

public class PropertyInformation {

    private String name;
    private Object value;
    private Class<?> type;
    private Annotation[] annotations;

    PropertyInformation(String name, Object value, Class<?> type, Annotation[] annotations) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
