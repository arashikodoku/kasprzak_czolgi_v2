package com.samsung.czolgi.fizyka;

import com.badlogic.gdx.Gdx;


/**
 * Prosty symulator fizyki.
 */
public class Symulator {

    static final float GRAWITACJA = 9.81f; // [m/s2]

    final Cialo[] ciala = new Cialo[256];

    public void dodaj(Cialo cialo) {
        for (int i = 0; i < 256; i++) {
            if (ciala[i] == null) {
                ciala[i] = cialo;
                break;
            }
        }
    }

    public void usun(Cialo cialo) {
        for (int i = 0; i < 256; i++) {
            if (ciala[i] == cialo) {
                ciala[i] = null;
                break;
            }
        }
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
        for (int i = 0; i < ciala.length; ++i) {
            Cialo cialo = ciala[i];
            if (cialo == null) continue;
            grawitacja(cialo, deltaT);
            przyspiesz(cialo, deltaT);
            przemiesc(cialo, deltaT);
        }
    }

    /**
     * S = s + V * Δt
     */
    void przemiesc(Cialo cialo, float deltaT) {
        cialo.x = cialo.x + cialo.predkoscX * deltaT;
        cialo.y = cialo.y + cialo.predkoscY * deltaT;
    }

    /**
     * V = v + a * Δt
     */
    void przyspiesz(Cialo cialo, float deltaT) {
        cialo.predkoscX += cialo.przyspX * deltaT;
        cialo.predkoscY += cialo.przyspY * deltaT;
    }

    /**
     * V = v + g * Δt
     */
    void grawitacja(Cialo cialo, float deltaT) {
        cialo.predkoscY += -GRAWITACJA * deltaT;
    }
}
