package de.afrouper.beans.impl;


import de.afrouper.beans.api.Bean;
import de.afrouper.beans.api.NoChangeTracking;

@NoChangeTracking
public interface Adress extends Bean {

    String getStreet();
    void setStreet(String street);
}
