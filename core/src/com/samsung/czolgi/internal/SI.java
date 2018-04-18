package com.samsung.czolgi.internal;

import com.badlogic.gdx.math.Vector2;
import com.samsung.czolgi.Czolg;
import com.samsung.czolgi.fizyka.Symulator;

/**

 */

public class SI {
    public static Vector2 dajOptymalnaPredkoscPocisku(final Czolg gracz, final Czolg si) {
        float dystans = gracz.getPosition().dst(si.getPosition());

        float v = (float)Math.sqrt(dystans * Symulator.GRAWITACJA / 2.0f);
        //dystans = 2 * Vx * Vy / g
        //Vx==Vy= <=> 2 * V^2 => dystans = 2 * V^2 / g
        //V = sqrt(dystans * g / 2)
        return new Vector2(-v, v);
    }
}
