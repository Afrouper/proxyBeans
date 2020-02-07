package de.afrouper.beans.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.BeanFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

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
    public void simpleBean() throws Exception {
        SimpleJsonTestBean bean = createSimpleBean();

        String json = gson.toJson(bean);

        JSONAssert.assertEquals(readExpectedJson("simpleJsonTestBean.json"), json, JSONCompareMode.STRICT);
    }

    @Test
    public void complexBean() throws Exception {
        ComplexJsonTestBean bean = BeanFactory.createBean(ComplexJsonTestBean.class);
        bean.setId("ID_42");
        bean.setSimpleBean(createSimpleBean());

        String json = gson.toJson(bean);
        System.out.println(json);
        JSONAssert.assertEquals(readExpectedJson("complexJsonTestBean.json"), json, JSONCompareMode.STRICT);
    }

    private SimpleJsonTestBean createSimpleBean() {
        SimpleJsonTestBean bean = BeanFactory.createBean(SimpleJsonTestBean.class);
        bean.setAge(42);
        bean.setName("Foo");
        bean.setChild(false);
        bean.setGender('M');
        bean.setHight(200);
        bean.setWidth(38.45);
        return bean;
    }

    private String readExpectedJson(String name) throws Exception {
        final String path = "/" + getClass().getPackage().getName().replace('.', '/');
        return FileUtils.readFileToString(new File(getClass().getResource(path + "/" + name).toURI()), StandardCharsets.UTF_8);
    }
}