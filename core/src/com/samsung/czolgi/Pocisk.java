package com.samsung.czolgi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by p.adamczyk on 10/6/17.
 */

public class Pocisk extends Sprite {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    private Texture texture;

    public Pocisk(Texture texture) {
        super(texture);
        this.texture = texture;
    }

    public void ustawPozycje(float kat, int x, int y) {
        setRotation(kat);
        setPosition(x, y);
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean czyTrafilWCzolg(Czolg czolg) {
        return czolg.getBoundingRectangle().overlaps(getBoundingRectangle());
    }

    public boolean czyPozaEkranem() {
        return getX() > GraCzolgi.EKRAN_SZEROKOSC || getX() < 0 ||
                getY() > GraCzolgi.EKRAN_WYSOKOSC || getY() < 0;
    }
}
