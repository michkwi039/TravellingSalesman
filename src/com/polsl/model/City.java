package com.polsl.model;

public class City {
    private String name;
    private Double xLength;
    private Double yLength;

    public City(String name, Double xLength, Double yLength) {
        this.name = name;
        this.xLength = xLength;
        this.yLength = yLength;
    }

    public Double distanceTo(City city) {
        Double x = this.xLength - city.xLength;
        Double y = this.yLength - city.yLength;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public String getName() {
        return name;
    }

    public Double getxLength() {
        return xLength;
    }

    public Double getyLength() {
        return yLength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setxLength(Double xLength) {
        this.xLength = xLength;
    }

    public void setyLength(Double yLength) {
        this.yLength = yLength;
    }


}
