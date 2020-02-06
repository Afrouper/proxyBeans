package de.afrouper.beans.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProxyBeanJsonSerializerTest {

    private Gson gson;

    @BeforeEach
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ProxyBeanTypeAdapterFactory());
        gson = gsonBuilder.create();
    }

    @Test
    public void serialize() {
        SimpleJsonTestBean bean = BeanFactory.createBean(SimpleJsonTestBean.class);
        bean.setAge(42);
        bean.setName("Foo");
        bean.setChild(false);

        PlainBean plainBean = new PlainBean();
        plainBean.setName("FooBar");

        String s = gson.toJson(plainBean);
        System.out.println(s);

        String json = gson.toJson(bean);
        System.out.println(json);
        assertNotNull(json);
    }
}