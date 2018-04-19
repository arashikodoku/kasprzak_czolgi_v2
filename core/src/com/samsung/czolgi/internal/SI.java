package com.samsung.czolgi.internal;

import com.badlogic.gdx.math.Vector2;
import com.samsung.czolgi.Czolg;
import com.samsung.czolgi.fizyka.Symulator;


public class SI {
    static float blad = 2;
    public static Vector2 dajOptymalnaPredkoscPocisku(final Czolg gracz, final Czolg si) {
        //TODO: Zaimplementuj obliczenia
//        return new Vector2(1, 1);
//agass
      float dystans = gracz.getPosition().dst(si.getPosition());
        float v = (float)Math.sqrt(dystans * Symulator.GRAWITACJA / 2.0f);
       v += (Math.random() - 0.5)* blad * v;
        blad *= Math.random() * 5;
        return new Vector2(-v, v);

    }

}


