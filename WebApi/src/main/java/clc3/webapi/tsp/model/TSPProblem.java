package clc3.webapi.tsp.model;

import java.util.ArrayList;
import java.util.List;

public class TSPProblem {
    Long id;
    String name;
    List<TSPCity> cities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TSPCity> getCities() {
        return cities;
    }

    public void setCities(List<TSPCity> cities) {
        this.cities = cities;
    }
}
