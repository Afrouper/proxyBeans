package de.afrouper.beans.json;

import de.afrouper.beans.api.Bean;

import java.util.Set;

public interface SetBean extends Bean {

    Set<SimpleJsonTestBean> getSet();
    void setSet(Set<SimpleJsonTestBean> set);
}
