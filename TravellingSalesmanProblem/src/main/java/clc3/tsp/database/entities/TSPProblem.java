package clc3.tsp.database.entities;

import clc3.common.AbstractEntity;
import clc3.common.utils.StringUtils;
import com.googlecode.objectify.annotation.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TSPProblem extends AbstractEntity<TSPProblem> {
    String name;
    List<TSPCity> cities;

    public TSPProblem() {
        this.name = StringUtils.EMPTY;
        this.cities = new ArrayList<>();
    }

    public TSPProblem(String name) {
        this.name = name;
        this.cities = new ArrayList<>();
    }

    public TSPProblem(List<TSPCity> cities) {
        this.name = StringUtils.EMPTY;
        this.cities = cities;
    }

    public TSPProblem(String name, List<TSPCity> cities) {
        this.name = name;
        this.cities = cities;
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
