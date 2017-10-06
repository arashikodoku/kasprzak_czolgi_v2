package com.samsung.czolgi;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Czolg extends Sprite {

    private static final int SCALE = 5;

    private final Sprite wiezyczka;

    public Czolg() {
        super(GraCzolgi.czolgTex);
        setScale(SCALE);

        wiezyczka = new Sprite(GraCzolgi.wiezyczkaTex);
        wiezyczka.setScale(SCALE);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        wiezyczka.setPosition(x, y);
    }

    public void draw(Batch batch) {
        super.draw(batch);
        wiezyczka.draw(batch);
    }

    public void obrocWiezyczke(float stopnie) {
        wiezyczka.setRotation((float) Math.toDegrees(stopnie));
    }
}
