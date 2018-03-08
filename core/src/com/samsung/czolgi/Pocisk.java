package com.samsung.czolgi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.samsung.czolgi.fizyka.Cialo;


public class Pocisk extends Sprite {

    final Cialo cialo;

    public Pocisk() {
        super(Assets.getPociskTex());
        this.cialo = new Cialo();
    }

    @Override
    public void draw(Batch batch) {
        setPosition(cialo.getX(), cialo.getY());
        super.draw(batch);
    }

    public boolean czyTrafilWCzolg(Czolg czolg) {
        return czolg.getBoundingRectangle().overlaps(getBoundingRectangle());
    }

    public boolean czyPozaEkranem() {
        return getX() > Gdx.graphics.getWidth() || getX() < 0 ||
                getY() > Gdx.graphics.getHeight() || getY() < 0;
    }
}
