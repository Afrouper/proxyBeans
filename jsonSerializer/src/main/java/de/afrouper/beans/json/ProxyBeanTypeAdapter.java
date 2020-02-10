package de.afrouper.beans.json;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanFactory;
import de.afrouper.beans.api.ext.BeanAccess;

import java.io.IOException;

class ProxyBeanTypeAdapter<T> extends TypeAdapter<Bean> {

    private TypeToken<T> type;

    public ProxyBeanTypeAdapter(TypeToken<T> type) {
        this.type = type;
    }

    @Override
    public void write(JsonWriter out, Bean value) throws IOException {
        BeanAccess<Bean> beanAccess = BeanFactory.getBeanAccess(value);
        try {
            out.beginObject();
            beanAccess.visit(new GsonVisitor(out), false);
        }
        finally {
            out.endObject();
        }
        beanAccess.resetTrackedChanges();
    }

    @Override
    public Bean read(JsonReader in) throws IOException {
        final GsonBeanReader reader = new GsonBeanReader();
        return reader.read(type, in);
    }
}
