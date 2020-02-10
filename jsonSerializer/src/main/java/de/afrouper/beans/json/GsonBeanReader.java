package de.afrouper.beans.json;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanFactory;
import de.afrouper.beans.api.ext.BeanAccess;

import java.io.IOException;
import java.util.Map;

public class GsonBeanReader {

    public GsonBeanReader() {
    }

    public <T, B extends Bean> B read(TypeToken<T> rootBeanType, JsonReader in) throws IOException {
        Class<? super T> type = rootBeanType.getRawType();
        if (!Bean.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Type " + type + " not an instance of Bean.");
        }
        final B bean = (B) BeanFactory.createBean((Class<? extends Bean>) type);
        return (B) read(bean, in);
        /*
        B bean = null;
        while (in.hasNext()) {
            JsonToken nextToken = in.peek();
            switch (nextToken) {
                case BEGIN_OBJECT:
                    //Root
                    in.beginObject();
                    final String nextName = in.nextName();
                    System.out.println(nextName);
                    bean = (B) read((Class<? extends Bean>) type, in);
                    in.endObject();
                    break;
                default:
                    throw new IllegalStateException("Unexpected JsonToken: " + nextToken);
            }
        }
        return bean;
        */
    }

    private Object read(Bean bean, JsonReader jsonReader) throws IOException {
        try {
            while (jsonReader.hasNext()) {
                JsonToken nextToken = jsonReader.peek();
                switch (nextToken) {
                    case BEGIN_ARRAY:
                        break;
                    case BEGIN_OBJECT:
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String attributeName = jsonReader.nextName();
                            Object value = read(bean, jsonReader);
                            System.out.println(attributeName + " - " + value);
                        }
                        jsonReader.endObject();
                        break;
                    case STRING:
                        jsonReader.nextString();
                        break;
                    case NUMBER:
                        jsonReader.nextDouble();
                        break;
                    case BOOLEAN:
                        jsonReader.nextBoolean();
                        break;
                    case NULL:
                        jsonReader.nextNull();
                        break;
                    default:
                        throw new IllegalStateException("Invalid JsonToken " + nextToken);
                }
            }
        } finally {
            //jsonReader.close();
        }

        return bean;
    }
}
