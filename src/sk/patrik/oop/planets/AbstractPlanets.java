package sk.patrik.oop.planets;

public abstract class AbstractPlanets {
    private int radiusOfOrbit;
    private int sizeOfPlanet;

    public AbstractPlanets(int radiusOfOrbit,int sizeOfPlanet){
        this.radiusOfOrbit = radiusOfOrbit;
        this.sizeOfPlanet = sizeOfPlanet;
    }
}
