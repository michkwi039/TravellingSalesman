package com.polsl.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Path implements Comparable<Path> {
    public LinkedList<City> cities;
    public Integer distance;
    public Path(LinkedList<City> cities, Integer distance){
        this.cities=cities;
        this.distance=distance;
    }


    @Override
    public int compareTo(Path path) {
        return this.distance-path.distance;
    }
}
