package de.afrouper.beans.json;

import de.afrouper.beans.api.Bean;

public interface ComplexJsonTestBean extends Bean {

    SimpleJsonTestBean getSimpleBean();
    void setSimpleBean(SimpleJsonTestBean bean);

    String getId();
    void setId(String id);
}
