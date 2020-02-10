package de.afrouper.beans.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyBeanJsonDeserializerTest {

    private Gson gson;

    @BeforeEach
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ProxyBeanTypeAdapterFactory());
        gson = gsonBuilder.create();
    }

    @Test
    public void read() throws Exception {
        final String json = TestHelper.readJson("simpleJsonTestBean.json");
        final SimpleJsonTestBean bean = gson.fromJson(json, SimpleJsonTestBean.class);
        Assertions.assertNotNull(bean);
        System.out.println(bean);

    }
}
