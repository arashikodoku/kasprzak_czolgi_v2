package com.samsung.czolgi;

import com.badlogic.gdx.input.GestureDetector;

public class DirectionGestureDetector extends GestureDetector {
    private static float startX;
    private static float startY;

    private static float stopX;
    private static float stopY;

    interface GestureListenerCallback {
        void callback(double angle, int force);
    }

    public DirectionGestureDetector(GestureListenerCallback gestureListenerCallback) {
        super(new DirectionGestureListener(gestureListenerCallback));
    }

    private static class DirectionGestureListener extends GestureDetector.GestureAdapter {
        GestureListenerCallback gestureListenerCallback;

        public DirectionGestureListener(GestureListenerCallback gestureListenerCallback) {
            this.gestureListenerCallback = gestureListenerCallback;
        }

        @Override
        public boolean touchDown (float x, float y, int pointer, int button) {
            startX = x;
            startY = y;

            return super.panStop(x, y, pointer, button);
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            stopX = x;
            stopY = y;

            gestureListenerCallback.callback(getAngle(), getForce());

//            System.out.println("delta x: " + getDeltaX());
//            System.out.println("delta y: " + getDeltaY());
//            System.out.println("angle: " + getAngle());
//            System.out.println("abs: " + getAbs());
//            System.out.println("force: " + getForce());

            return super.panStop(x, y, pointer, button);
        }
    }

    public static int getForce() {
        if (getAbs() > 1000)
            return 100;
        else
            return (int) (getAbs() * 100 / 1000);
    }

    public static double getAngle() {
        return Math.toDegrees(Math.atan(-getDeltaY() / getDeltaX()));
    }

    public static double getAbs() {
        return Math.sqrt(getDeltaX() * getDeltaX() + getDeltaY() * getDeltaY());
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
