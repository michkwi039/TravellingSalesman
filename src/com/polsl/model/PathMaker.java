package com.polsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


public class PathMaker {

    public LinkedList<Path> CreateFirstGeneration(LinkedList<City> cities)throws CityNotFoundException{
        City staringCity= cities.remove(0);

        LinkedList<Path> paths=new LinkedList<>();
        Random generator =new Random();
        for(int i=0;i<10;i++){
            LinkedList<City> temporary=new LinkedList<>();
            temporary.add(staringCity);
            City currentCity = staringCity;
            LinkedList<City> citiesLeft = new LinkedList<>(cities);
            Integer distanceTravelled=0;
            while(citiesLeft.size()!=0){
                Integer next = generator.nextInt(citiesLeft.size());
                City nextCity= citiesLeft.get(next);
                temporary.add(nextCity);
                currentCity=nextCity;
                citiesLeft.remove(next);
            }
            temporary.add(staringCity);
            paths.add(createPath(temporary));
            //System.out.println(distanceTravelled);
        }
        return paths;
    }


    public Path getShortest(LinkedList<Path> paths){
        Path best=paths.get(0);
        for (Path i: paths) {
            if(i.distance<best.distance)
                best=i;
        }
        return best;
    }

    public Path pathMutator(Path path){
        Random generator =new Random();
        Integer i =generator.nextInt(path.cities.size()-2)+1;
        Integer j =generator.nextInt(path.cities.size()-2)+1;
        while(i.equals(j)){
            j =generator.nextInt(path.cities.size()-2)+1;
        }
        LinkedList<City> mutated=new LinkedList<>(path.cities);
        Collections.swap(mutated,i,j);
        return createPath(mutated);
    }

    public Path pathCrosser(Path path1,Path path2){
        Integer l =path1.cities.size()/2;
        LinkedList<City> newCityPath= new LinkedList<>(path1.cities.subList(0,l));
        newCityPath.forEach(a-> path2.cities.remove(a));
        newCityPath.addAll(path2.cities);
        newCityPath.add(newCityPath.get(0));
        return createPath(newCityPath);
    }

    public Path createPath(LinkedList<City> cities){

        Double distance=0.0;
        for (int i =0 ;i<cities.size()-1;i++) {
            distance+= cities.get(i).distanceTo(cities.get(i+1));
        }
        return new Path(cities,distance);
    }

    public LinkedList<Path> subGeneration(LinkedList<Path> paths,Integer sizeAfter){
        Collections.sort(paths);
        return new LinkedList<>(paths.subList(0,sizeAfter));
    }
}
