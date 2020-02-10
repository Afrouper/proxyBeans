package de.afrouper.beans.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanFactory;

import java.io.IOException;
import java.util.ArrayList;

public class GsonBeanReader {

    public GsonBeanReader() {
    }

    public <T, B extends Bean> B read(TypeToken<T> rootBeanType, JsonReader in) throws IOException {
        Class<? super T> type = rootBeanType.getRawType();
        if (!Bean.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Type " + type + " not an instance of Bean.");
        }
        if(in.hasNext()) {
            JsonToken jsonToken = in.peek();
            if(JsonToken.BEGIN_OBJECT.equals(jsonToken)) {
                in.beginObject();
                B bean = (B) read((Class<? extends Bean>) type, in);
                in.endObject();
                return bean;
            }
            else {
                throw new IllegalStateException("Expected JsonTonken from Type " + JsonToken.BEGIN_OBJECT + ", Got: " + jsonToken);
            }
        }
        else {
            throw new IllegalStateException("JsonReader has nothing to read.");
        }
    }


    private Object read(Class<? extends Bean> beanType, JsonReader in) throws IOException {
        Bean bean = BeanFactory.createBean(beanType);
        String name = null;
        while (in.hasNext()) {
            JsonToken token = in.peek();
            switch (token) {
                case NAME:
                    name = in.nextName();
                    break;
                case STRING:
                    String s = in.nextString();
                    //TODO: bean.setAttribute(name, string);
                    break;
                case NUMBER:
                    //TODO: Determine if extracting an long, int, float or double
                    double d = in.nextDouble();
                    //TODO: bean.setAttribute(name, d);
                    break;
                case BOOLEAN:
                    boolean b = in.nextBoolean();
                    //TODO: bean.setAttribute(name, b);
                    break;
                case NULL:
                    in.nextNull();
                    //TODO: bean.setAttribute(name, null);
                    break;
                case BEGIN_OBJECT:
                    in.beginObject();
                    Class<? extends Bean> objBeanType = null;  //TODO: Determine Bean class
                    Object objValue = read(objBeanType, in);
                    //TODO: bean.setAttribute(name, objValue);
                    in.endObject();
                    break;
                case BEGIN_ARRAY:
                    //TODO: Handle Array
                    in.beginArray();
                    Class<? extends Bean> beanTypeInArray = null;  //TODO: Determine Bean class
                    ArrayList<Object> list = new ArrayList<>();
                    while(in.peek().equals(JsonToken.BEGIN_OBJECT)) {
                        list.add(read(beanTypeInArray, in));
                    }
                    //TODO: bean.setAttribute(name, objValue);
                    in.endArray();
                    break;
            }
        }
        return bean;
    }
}
