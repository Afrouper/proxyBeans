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
    public void simpleBean() {
        SimpleJsonTestBean bean = createSimpleBean();

        String json = gson.toJson(bean);
        System.out.println(json);
        assertNotNull(json);
    }

    @Test
    public void complexBean() {
        ComplexJsonTestBean bean = BeanFactory.createBean(ComplexJsonTestBean.class);
        SimpleJsonTestBean simpleBean = createSimpleBean();
        bean.setId("ID_42");
        bean.setSimpleBean(simpleBean);

        String json = gson.toJson(bean);
        System.out.println(json);
        assertNotNull(json);
    }

    private SimpleJsonTestBean createSimpleBean() {
        SimpleJsonTestBean bean = BeanFactory.createBean(SimpleJsonTestBean.class);
        bean.setAge(42);
        bean.setName("Foo");
        bean.setChild(false);
        return bean;
    }
}