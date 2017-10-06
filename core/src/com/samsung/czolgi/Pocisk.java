package com.samsung.czolgi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by p.adamczyk on 10/6/17.
 */

public class Pocisk extends Rectangle {
    public static final int WIDTH = 92;
    public static final int HEIGHT = 92;

    private Texture texture;

    public Pocisk(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
