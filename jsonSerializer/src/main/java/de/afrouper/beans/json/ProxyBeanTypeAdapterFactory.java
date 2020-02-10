package de.afrouper.beans.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import de.afrouper.beans.api.Bean;

public class ProxyBeanTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if(Bean.class.isAssignableFrom(type.getRawType())) {
            return (TypeAdapter<T>) new ProxyBeanTypeAdapter(type);
        }
        return null;
    }
}
