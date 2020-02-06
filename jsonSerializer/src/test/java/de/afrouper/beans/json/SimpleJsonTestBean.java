package de.afrouper.beans.json;

import de.afrouper.beans.api.Bean;

public interface SimpleJsonTestBean extends Bean {

    String getName();
    void setName(String name);

    int getAge();
    void setAge(int age);

    boolean isChild();
    void setChild(boolean child);
}
