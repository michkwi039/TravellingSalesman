package com.polsl.model;

import java.util.ArrayList;

public class City {
    public String name;
    public ArrayList<DistanceDTO> distances;
    public Double xLength;
    public Double yLength;

    public Double distanceTo(City city){
        /*String name=city.name;

        for (DistanceDTO i:distances) {
            if(i.name.equals(city.name))
                return i.distance;
        }
        return 0;*/

        Double x=this.xLength-city.xLength;
        Double y= this.yLength-city.yLength;
       return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));

    }
    public City(String name,ArrayList<DistanceDTO> distances){
        this.name=name;
        this.distances=distances;
    }
    public void addDistance(String name,Integer distance){
        distances.add(new DistanceDTO(name,distance));
    }

    public class DistanceDTO{
        public String name;
        public Integer distance;
        DistanceDTO(String name, Integer distance){
            this.name=name;
            this.distance=distance;
        }
    }


}
