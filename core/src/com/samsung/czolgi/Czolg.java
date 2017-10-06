package com.samsung.czolgi;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Czolg extends Sprite {

    private static final int SCALE = 5;

    private final Sprite wiezyczka;

    public Czolg() {
        super(GraCzolgi.czolgTex);
        setScale(SCALE);
        setPosition(100, 100);

        wiezyczka = new Sprite(GraCzolgi.wiezyczkaTex);
        wiezyczka.setScale(SCALE);
        wiezyczka.setPosition(100, 100);
        wiezyczka.setRotation(45);
    }


    public void draw(Batch batch) {
        super.draw(batch);
        wiezyczka.draw(batch);
    }

    public void obrocWiezyczke(float stopnie) {
        wiezyczka.rotate(stopnie);
    }
}
