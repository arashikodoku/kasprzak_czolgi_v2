package com.samsung.czolgi;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class Czolg extends Sprite {

    private static final int SCALE = 1;

    private final Sprite wiezyczka;

    public Czolg() {
        super(Assets.getCzolgTex());
        setScale(SCALE);

        wiezyczka = new Sprite(Assets.getWiezyczkaTex());
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

    public Vector2 getPosition() {
        return new Vector2(getX(), getY()).add(getWidth()/2, getHeight()/2);
    }
}
