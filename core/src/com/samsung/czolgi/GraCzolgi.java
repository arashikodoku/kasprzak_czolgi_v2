package com.samsung.czolgi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class GraCzolgi extends ApplicationAdapter implements WykrywaczGestow.GestureListenerCallback {

    public static final int EKRAN_SZEROKOSC = 1600;
    public static final int EKRAN_WYSOKOSC = 800;

    private SpriteBatch batch;
    private Music music;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;


    private Pocisk pocisk;

    private boolean pociskLeci;

    private boolean gracz2Strzela;
    // zasoby
    static Texture czolgTex;
    static Texture wiezyczkaTex;

    // Gracze
    Czolg gracz1;
    Czolg gracz2;

    private Czolg czolgZagrozony;
    private Czolg czolgStrzelajacy;

    private Vector2 punktGraniczny;

    private boolean wgore;

    private BitmapFont font;
    private BitmapFontCache wygranaTekst;
    private BitmapFontCache przegranaTekst;

    private enum Stan {
        WIN, LOSE,
        ONPROGRES
    }

    ;

    private Stan aktualnyStan = Stan.ONPROGRES;


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
        ustawTeksst();


    }

    private void ustawTeksst() {
        font = new BitmapFont(false);
        font.getData().scale(10);
        wygranaTekst = font.newFontCache();
        wygranaTekst.setColor(Color.GREEN);
        wygranaTekst.setText("YOU WIN", EKRAN_SZEROKOSC / 4, EKRAN_WYSOKOSC / 2);
        przegranaTekst = font.newFontCache();
        przegranaTekst.setColor(Color.RED);
        przegranaTekst.setText("YOU LOSE", EKRAN_SZEROKOSC / 4, EKRAN_WYSOKOSC / 2);

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

        WykrywaczGestow gestureDetector = new WykrywaczGestow(this);
        Gdx.input.setInputProcessor(gestureDetector);


        gracz1.draw(batch);
        gracz2.draw(batch);

        if (pociskLeci) {
            pocisk.draw(batch);
        }

        if (aktualnyStan == Stan.WIN) {
            wygranaTekst.draw(batch);
        }


        switch (aktualnyStan) {
            case WIN:
                wygranaTekst.draw(batch);
                break;
            case LOSE:
                przegranaTekst.draw(batch);
                break;
        }


        batch.end();

        if (pociskLeci) {
            sprawdzStanPocisku();
        }


    }

    private void sprawdzStanPocisku() {
        if (pociskLeci) {
            if (pocisk.czyTrafilWCzolg(czolgZagrozony)) {
                if (gracz2Strzela) {
                    aktualnyStan = Stan.LOSE;
                } else {
                    aktualnyStan = Stan.WIN;
                }
                //TODO pokaz texture wybuchu
            } else if (pocisk.czyPozaEkranem()) {
                pociskLeci = false;
                if (gracz2Strzela) {
                    gracz2Strzela = false;
                } else {
                    strzelaGracz2();
                }
            } else {
                //TODO fix this
                przesunPocisk(punktGraniczny.x / 40, wgore ? punktGraniczny.y / 40 : -punktGraniczny.y / 40);
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
        if (!gracz2Strzela) {
            System.out.println("Angle: " + kat);
            System.out.println("Moc: " + moc);
            wystrzelPocisk(kat, moc, gracz1, gracz2);
        }
    }

    private void wystrzelPocisk(double kat, int moc, Czolg czolgStrzelajacy, Czolg czolgZagrozony) {
        float delta = EKRAN_SZEROKOSC * moc / 100;
        czolgStrzelajacy.obrocWiezyczke((float) kat);
        punktGraniczny = new Vector2(delta * (float) Math.cos(kat), delta * (float) Math.sin(kat));
        pocisk.ustawPozycje((float) kat, czolgStrzelajacy.getX() + czolgStrzelajacy.getWidth(), czolgStrzelajacy.getY() + czolgStrzelajacy.getHeight());
        this.czolgZagrozony = czolgZagrozony;
        pociskLeci = true;
        wgore = true;
    }

    private void rysujZiemie() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(0, 0, 1600, 200);
        shapeRenderer.end();
    }


    private void przesunPocisk(float x, float y) {
        if (pocisk.getX() > punktGraniczny.x && pocisk.getY() > punktGraniczny.y) {
            wgore = false;
        }
        pocisk.ustawPozycje(0, pocisk.getX() + x, pocisk.getY() + y);

    }

    private void strzelaGracz2() {
        gracz2Strzela = true;
        Random random = new Random();
        pocisk.flip(true, false);
        float katStrzalu = 90.f + random.nextFloat() * 90.f;
        wystrzelPocisk(Math.toRadians(katStrzalu), random.nextInt(95) + 5, gracz2, gracz1);
    }


}
