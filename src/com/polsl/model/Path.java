package com.polsl.model;

import java.util.concurrent.CopyOnWriteArrayList;

public class Path implements Comparable<Path> {
    public CopyOnWriteArrayList<City> cities;
    private Double distance;
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
            stringBuilder.append(c.getName()+" ");
        }
        stringBuilder.append(" distance: " + distance);
        return stringBuilder.toString();
    }

    public CopyOnWriteArrayList<City> getCities() {
        return cities;
    }

    public void setCities(CopyOnWriteArrayList<City> cities) {
        this.cities = cities;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
