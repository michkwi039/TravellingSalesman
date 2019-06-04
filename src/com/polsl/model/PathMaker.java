package com.polsl.model;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;

public class PathMaker {

    public CopyOnWriteArrayList<Path> CreateFirstGeneration(CopyOnWriteArrayList<City> cities, City staringCity) {

        CopyOnWriteArrayList<Path> paths = new CopyOnWriteArrayList<>();
        Random generator = new Random();
        for (int i = 0; i < 10; i++) {
            CopyOnWriteArrayList<City> temporary = new CopyOnWriteArrayList<>();
            temporary.add(staringCity);
            City currentCity = staringCity;
            CopyOnWriteArrayList<City> citiesLeft = new CopyOnWriteArrayList<>(cities);
            Integer distanceTravelled = 0;
            while (citiesLeft.size() > 0) {
                Integer next = generator.nextInt(citiesLeft.size());
                City nextCity = citiesLeft.get(next);
                temporary.add(nextCity);
                currentCity = nextCity;
                citiesLeft.remove(nextCity);
            }
            temporary.add(staringCity);
            paths.add(createPath(temporary));
        }
        return paths;
    }

    public Path getShortest(CopyOnWriteArrayList<Path> paths) {
        Path best = paths.get(0);
        for (Path i : paths) {
            if (i.getDistance() < best.getDistance())
                best = i;
        }
        return best;
    }

    public Path pathMutator(Path path) {
        Random generator = new Random();
        Integer i = generator.nextInt(path.cities.size() - 2) + 1;
        Integer j = generator.nextInt(path.cities.size() - 2) + 1;
        while (i.equals(j)) {
            j = generator.nextInt(path.cities.size() - 2) + 1;
        }
        CopyOnWriteArrayList<City> mutated = new CopyOnWriteArrayList<>(path.cities);
        Collections.swap(mutated, i, j);
        return createPath(mutated);
    }

    public Path pathCrosser(Path path1, Path path2) {
        Integer l = path1.cities.size() / 2;
        CopyOnWriteArrayList<City> newCityPath = new CopyOnWriteArrayList<>(path1.cities.subList(0, l));
        CopyOnWriteArrayList<City> cities = new CopyOnWriteArrayList<>(path2.cities);
        newCityPath.forEach(a -> cities.remove(a));
        newCityPath.addAll(cities);
        return createPath(newCityPath);
    }

    public Path choosePath(CopyOnWriteArrayList<Path> paths) {
        Random generator = new Random();
        Integer next = generator.nextInt(paths.size());
        return paths.get(next);
    }

    public Path createPath(CopyOnWriteArrayList<City> cities) {
        Double distance = 0.0;
        for (int i = 0; i < cities.size() - 1; i++) {
            distance += cities.get(i).distanceTo(cities.get(i + 1));
        }
        return new Path(cities, distance);
    }

    public CopyOnWriteArrayList<Path> subGeneration(CopyOnWriteArrayList<Path> paths, Integer sizeAfter) {
        Collections.sort(paths);
        return new CopyOnWriteArrayList<>(paths.subList(0, sizeAfter));
    }
}
