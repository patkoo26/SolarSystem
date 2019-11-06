package sk.patrik.oop.planets;


import javafx.animation.Animation;
import sun.awt.windows.WInputMethod;

public class Sun extends AbstractPlanets{


    public Sun(int radiusOfOrbit, int sizeOfPlanet) {
        super(radiusOfOrbit, sizeOfPlanet);
        WInputMethod wInputMethod = new WInputMethod();

    }


}
