package com.polsl.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Path implements Comparable<Path> {
    public LinkedList<City> cities;
    public Double distance;
    public Path(LinkedList<City> cities, double distance){
        this.cities=cities;
        this.distance=distance;
    }


    @Override
    public int compareTo(Path path) {
        if(this.distance-path.distance>0)
            return 1;
        else return -1;
    }
}
