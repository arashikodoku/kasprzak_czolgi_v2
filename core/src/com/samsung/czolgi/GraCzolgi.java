package com.samsung.czolgi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GraCzolgi extends ApplicationAdapter implements DirectionGestureDetector.GestureListenerCallback {

    public static final int EKRAN_SZEROKOSC = 1600;
    public static final int EKRAN_WYSOKOSC = 800;

    private SpriteBatch batch;
    private Music music;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;


    private Pocisk pocisk;

    private boolean pociskLeci;

    // zasoby
    static Texture czolgTex;
    static Texture wiezyczkaTex;

    // Gracze
    Czolg gracz1;
    Czolg gracz2;

    private Czolg czolgZagrozony;

    private Vector2 trasaPocisku;


    @Override
    public void create() {
        batch = new SpriteBatch();

        czolgTex = new Texture("tank.png");
        wiezyczkaTex = new Texture("turret.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("XX.mp3"));
        music.setLooping(true);
        music.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, EKRAN_SZEROKOSC, EKRAN_WYSOKOSC);

        stworzPocisk();
        stworzCzolgi();
    }

    private void stworzCzolgi() {
        gracz1 = new Czolg();
        gracz1.setPosition(100, 100);

        gracz2 = new Czolg();
        gracz2.setPosition(1600 - 100, 100);
        gracz2.flip(true, false);
    }

    private void stworzPocisk() {
        Texture pociskTexture = new Texture("pocisk.png");
        pocisk = new Pocisk(pociskTexture);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        rysujZiemie();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        DirectionGestureDetector gestureDetector = new DirectionGestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);


        gracz1.draw(batch);
        gracz2.draw(batch);

        if (pociskLeci) {
            pocisk.draw(batch);
        }
        batch.end();

        if (pociskLeci) {
            sprawdzStanPocisku();
        }


    }

    private void sprawdzStanPocisku() {
        if (pociskLeci) {
            if (pocisk.czyTrafilWCzolg(czolgZagrozony)) {
                //TODO pokaz texture wybuchu
            } else if (pocisk.czyPozaEkranem()) {
                pociskLeci = false;
            } else {
                //TODO fix this
                przesunPocisk(trasaPocisku.x/40, trasaPocisku.y/40);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        pocisk.getTexture().dispose();
    }

    @Override
    public void callback(double kat, int moc) {
        System.out.println("Angle: " + kat);
        System.out.println("Moc: " + moc);
        wystrzelPocisk(kat, moc);
    }

    private void wystrzelPocisk(double kat, int moc) {
        float delta = EKRAN_SZEROKOSC * moc / 100;
        trasaPocisku = new Vector2(delta * (float)Math.cos(kat), delta * (float)Math.sin(kat));
        pocisk.ustawPozycje((float) kat, gracz1.getX() + gracz1.getWidth(), gracz2.getY() + gracz2.getHeight());
        czolgZagrozony = gracz2;
        pociskLeci = true;
    }

    private void rysujZiemie() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(0, 0, 1600, 200);
        shapeRenderer.end();
    }


    private void wystrzelPocisk(float kat, int x, int y) {
        pocisk.ustawPozycje(kat, x, y);

        pociskLeci = true;
    }


    private void przesunPocisk(float x, float y) {

        pocisk.ustawPozycje(0, pocisk.getX() + x, pocisk.getY() + y);
    }



}
