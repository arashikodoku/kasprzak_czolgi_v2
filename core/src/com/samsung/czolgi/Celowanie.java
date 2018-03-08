package com.samsung.czolgi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 *
 */

public class Celowanie extends InputAdapter {

    final ShapeRenderer renderer = new ShapeRenderer();

    private final Vector2 startTouch = new Vector2();
    private final Vector2 currentTouch = new Vector2();

    private boolean touching;

    public Celowanie() {
        Gdx.input.setInputProcessor(this);
    }

    public void draw(Camera camera) {
        if (touching) {
            //renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.RED);
            renderer.line(startTouch, currentTouch);
            renderer.end();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer > 1) {
            return super.touchDown(screenX, screenY, pointer, button);
        }
        touching = true;
        startTouch.set(screenX, Gdx.graphics.getHeight()-screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer > 1) {
            return super.touchDragged(screenX, screenY, pointer);
        }
        currentTouch.set(screenX, Gdx.graphics.getHeight()-screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer > 1) {
            return super.touchUp(screenX, screenY, pointer, button);
        }
        touching = false;
        return true;
    }

    public boolean isTouching() {
        return touching;
    }

    public Vector2 getCel() {
        return currentTouch.cpy().sub(startTouch);
    }

    public float getForce() {
        return currentTouch.cpy().sub(startTouch).len();
    }
}
