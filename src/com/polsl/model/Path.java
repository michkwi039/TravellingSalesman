package com.polsl.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Path implements Comparable<Path> {
    public CopyOnWriteArrayList<City> cities;
    public Double distance;
    public Path(CopyOnWriteArrayList<City> cities, double distance){
        this.cities=cities;
        this.distance=distance;
    }


    @Override
    public int compareTo(Path path) {
        if(this.distance-path.distance>0)
            return 1;
        else return -1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for (City c: cities) {
            stringBuilder.append(c.name+" ");
        }
        stringBuilder.append("Dystans:" + distance);
        return stringBuilder.toString();
    }
}
