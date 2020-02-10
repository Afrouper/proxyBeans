package de.afrouper.beans.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.afrouper.beans.api.BeanFactory;
import de.afrouper.beans.api.BeanSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;

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
        JSONAssert.assertEquals(TestHelper.readJson("simpleJsonTestBean.json"), json, JSONCompareMode.STRICT);
    }

    @Test
    public void complexBean() throws Exception {
        ComplexJsonTestBean bean = BeanFactory.createBean(ComplexJsonTestBean.class);
        bean.setId("ID_42");
        bean.setSimpleBean(createSimpleBean());

        String json = gson.toJson(bean);
        JSONAssert.assertEquals(TestHelper.readJson("complexJsonTestBean.json"), json, JSONCompareMode.STRICT);
    }

    @Test
    public void listBean() throws Exception {
        final ListBean listBean = BeanFactory.createBean(ListBean.class);
        List<SimpleJsonTestBean> list = BeanFactory.createBeanList(SimpleJsonTestBean.class);
        for(int i = 0; i < 10; ++i) {
            final SimpleJsonTestBean bean = createSimpleBean_small();
            bean.setName(bean.getName() + "_" + i);
            list.add(bean);
        }
        listBean.setList(list);

        final String json = gson.toJson(listBean);
        JSONAssert.assertEquals(TestHelper.readJson("listBean.json"), json, JSONCompareMode.STRICT);
    }

    @Test
    public void setBean() throws Exception {
        final SetBean setBean = BeanFactory.createBean(SetBean.class);
        BeanSet<SimpleJsonTestBean> set = BeanFactory.createBeanSet(SimpleJsonTestBean.class);
        for(int i = 0; i < 10; ++i) {
            final SimpleJsonTestBean bean = createSimpleBean_small();
            bean.setName(bean.getName() + "_" + i);
            set.add(bean);
        }
        setBean.setSet(set);

        final String json = gson.toJson(setBean);
        JSONAssert.assertEquals(TestHelper.readJson("setBean.json"), json, JSONCompareMode.LENIENT);
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

    private SimpleJsonTestBean createSimpleBean_small() {
        SimpleJsonTestBean bean = BeanFactory.createBean(SimpleJsonTestBean.class);
        bean.setName("Lutz");
        return bean;
    }
}