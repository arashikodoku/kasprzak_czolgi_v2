package com.samsung.czolgi.fizyka;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

/**
 * Prosty symulator fizyki.
 */
public class Symulator {

    private static final float GRAWITACJA = 9.81f; // [m/s2]

    private final List<Cialo> ciala = new ArrayList<Cialo>();

    public void add(Cialo cialo) {
        ciala.add(cialo);
    }

    public void remove(Cialo cialo) {
        ciala.remove(cialo);
    }

    /**
     * Wywoluj funkcje symulacji fizyki w dla kazdej klatki obrazu.
     *
     * Ta funkcja symuluje fizyke w czasie rzeczywistym.
     */
    public void symuluj() {
        symuluj(Gdx.graphics.getDeltaTime());
    }

    /**
     * Tak jak wyzej, lecz pozwala na manipulacje czasem. ;-)
     *
     * @param deltaT delta time
     */
    public void symuluj(float deltaT) {
        for (Cialo cialo : ciala) {
            grawitacja(cialo, deltaT);
            przyspiesz(cialo, deltaT);
            przemiesc(cialo, deltaT);
        }
    }

    /**
     * s = V * dt
     */
    private void przemiesc(Cialo cialo, float deltaT) {
        cialo.x += cialo.predkoscX * deltaT;
        cialo.y += cialo.predkoscY * deltaT;
    }

    /**
     * v = a * dt
     */
    private void przyspiesz(Cialo cialo, float deltaT) {
        cialo.predkoscX += cialo.przyspX * deltaT;
        cialo.predkoscY += cialo.przyspY * deltaT;
    }

    /**
     * v = g * dt
     */
    private void grawitacja(Cialo cialo, float deltaT) {
        cialo.predkoscY += -GRAWITACJA * deltaT;
    }
}
