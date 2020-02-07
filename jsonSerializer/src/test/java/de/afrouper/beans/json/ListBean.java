package de.afrouper.beans.json;

import de.afrouper.beans.api.Bean;

import java.util.List;

public interface ListBean extends Bean {

    List<SimpleJsonTestBean> getList();
    void setList(List<SimpleJsonTestBean> list);
}
