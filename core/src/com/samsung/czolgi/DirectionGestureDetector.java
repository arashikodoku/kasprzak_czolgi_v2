package com.samsung.czolgi;

import com.badlogic.gdx.input.GestureDetector;

public class DirectionGestureDetector extends GestureDetector {
    private static float startX;
    private static float startY;

    private static float stopX;
    private static float stopY;

    public DirectionGestureDetector() {
        super(new DirectionGestureListener());
    }

    private static class DirectionGestureListener extends GestureDetector.GestureAdapter {
        public DirectionGestureListener() {
            super();
        }

        @Override
        public boolean touchDown (float x, float y, int pointer, int button) {
            startX = x;
            startY = y;

//            System.out.println("touch down x: " + x);
//            System.out.println("touch down y: " + y);
//
//            System.out.println("touch down pointer: " + pointer);
//            System.out.println("touch down button: " + button);

            return super.panStop(x, y, pointer, button);
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            stopX = x;
            stopY = y;

            System.out.println("delta x: " + getDeltaX());
            System.out.println("delta y: " + getDeltaY());

//            System.out.println("pan stop x: " + x);
//            System.out.println("pan stop y: " + y);
//
//            System.out.println("pan stop pointer: " + pointer);
//            System.out.println("pan stop button: " + button);

            return super.panStop(x, y, pointer, button);
        }
    }

    public static float getDeltaX() {
        return stopX - startX;
    }

    public static float getDeltaY() {
        return stopY - startY;
    }

    public static float getStartX() {
        return startX;
    }

    public static float getStartY() {
        return startY;
    }

    public static float getStopX() {
        return stopX;
    }

    public static float getStopY() {
        return stopY;
    }
}
