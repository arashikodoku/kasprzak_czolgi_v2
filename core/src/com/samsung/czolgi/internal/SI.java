package com.samsung.czolgi.internal;

import com.badlogic.gdx.math.Vector2;
import com.samsung.czolgi.Czolg;

/**

 */

public class SI {
    public static Vector2 dajOptymalnaPredkoscPocisku(final Czolg gracz, final Czolg si) {
        //dystans = V^2 * sin(2a) / g
        //Vx==Vy= <=> 2a==90 => dystans = V^2 / g
        //V = sqrt(dystans * g)
        return new Vector2(0.0f, 0.0f);
    }
}
