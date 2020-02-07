package de.afrouper.beans.json;

import de.afrouper.beans.api.Bean;

public interface SimpleJsonTestBean extends Bean {

    String getName();
    void setName(String name);

    int getAge();
    void setAge(int age);

    boolean isChild();
    void setChild(boolean child);

    char getGender();
    void setGender(char gender);

    long getHight();
    void setHight(long hight);

    double getWidth();
    void setWidth(double width);
}
