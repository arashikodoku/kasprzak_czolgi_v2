package com.samsung.czolgi;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.samsung.czolgi.fizyka.Cialo;
import com.samsung.czolgi.fizyka.Symulator;

/**
 *
 */

public class Trajektoria {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final Symulator symulator = new Symulator();

    private final Celowanie celowanie;
    private final Czolg czolg;

    Trajektoria(Celowanie celowanie, Czolg czolg) {
        this.celowanie = celowanie;
        this.czolg = czolg;
    }

    public void draw(Camera camera) {
        if (celowanie.isTouching()) {
            Vector2 positon = czolg.getPosition();
            Cialo cialo = new Cialo();
            cialo.setPozycja(positon);
            cialo.setPredkosc(celowanie.getCel());
            symulator.add(cialo);

            Vector2 poprzedniaPozycja = positon.cpy();
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Color color = new Color(Color.WHITE); // white
            for (int i = 0; i < 20; ++i) {
                symulator.symuluj(0.5f);
                shapeRenderer.setColor(color);
                shapeRenderer.line(poprzedniaPozycja.x, poprzedniaPozycja.y, cialo.getX(), cialo.getY());
                poprzedniaPozycja.set(cialo.getX(), cialo.getY());
                color.a -= 1.f/20.f;
            }
            shapeRenderer.end();
        }
    }
}
