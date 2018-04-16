package clc3.webapi.tsp.requests;

import clc3.webapi.tsp.model.TSPCity;

import java.util.Collection;

public class TSPProblemAddRequest {
    String name;
    Collection<TSPCity> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<TSPCity> getCities() {
        return cities;
    }

    public void setCities(Collection<TSPCity> cities) {
        this.cities = cities;
    }
}
