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
import com.badlogic.gdx.math.Vector2;
import com.samsung.czolgi.fizyka.Symulator;

import java.util.Random;

public class GraCzolgi extends ApplicationAdapter {

    public static final int EKRAN_SZEROKOSC = 800;
    public static final int EKRAN_WYSOKOSC = 480;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private final Symulator symulator = new Symulator();

    private Celowanie celowanie;
    private Trajektoria trajektoria;

    private Pocisk pocisk;

    private Ziemia ziemia;

    private boolean pociskLeci;

    private boolean gracz2Strzela;

    // Gracze
    Czolg gracz1;
    Czolg gracz2;

    private Czolg czolgZagrozony;
    private Czolg czolgStrzelajacy;

    private BitmapFontCache wygranaTekst;
    private BitmapFontCache przegranaTekst;

    private enum Stan {
        WIN, LOSE,
        ONPROGRES
    }

    private Stan aktualnyStan = Stan.ONPROGRES;

    private final ZasadyGry zasadyGry = new ZasadyGry(this);


    @Override
    public void create() {
        Assets.initialize();
        zasadyGry.nowaGra();

        batch = new SpriteBatch();

        Music music = Assets.getMusicBg();
        music.setLooping(true);
        music.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, EKRAN_SZEROKOSC, EKRAN_WYSOKOSC);

        ziemia = new Ziemia();
        stworzPocisk();
        stworzCzolgi();
        przygotujTeksty();

        celowanie = new Celowanie();
        trajektoria = new Trajektoria(celowanie, gracz1);
    }

    private void przygotujTeksty() {
        BitmapFont font = Assets.getFont();
        wygranaTekst = font.newFontCache();
        wygranaTekst.setColor(new Color(0, 0, 0, 1));
        wygranaTekst.setText("YOU WIN", Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2);
        przegranaTekst = font.newFontCache();
        przegranaTekst.setColor(Color.RED);
        przegranaTekst.setText("YOU LOSE", Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2);

    }

    private void stworzCzolgi() {
        gracz1 = new Czolg();
        gracz1.setPosition(100, 100);

        gracz2 = new Czolg();
        gracz2.setPosition(EKRAN_SZEROKOSC - 100, 100);
        gracz2.flip(true, false);
    }

    private void stworzPocisk() {
        pocisk = new Pocisk();
        symulator.add(pocisk.cialo);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        symulator.symuluj();
        camera.update();

        ziemia.draw(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

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

        celowanie.draw(camera);
        trajektoria.draw(camera);

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
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.dispose();
    }

    private void wystrzelPocisk(double kat, int moc, Czolg czolgStrzelajacy, Czolg czolgZagrozony) {
        float delta = Gdx.graphics.getWidth() * moc / 100;
        czolgStrzelajacy.obrocWiezyczke((float) kat);
        Vector2 sila = new Vector2(delta * (float) Math.cos(kat), delta * (float) Math.sin(kat));
        pocisk.cialo.setPozycja(czolgStrzelajacy.getPosition());
        pocisk.cialo.setPredkosc(sila);
        this.czolgZagrozony = czolgZagrozony;
        pociskLeci = true;
    }

    private void strzelaGracz2() {
        gracz2Strzela = true;
        Random random = new Random();
        pocisk.flip(true, false);
        float katStrzalu = 90.f + random.nextFloat() * 90.f;
        wystrzelPocisk(Math.toRadians(katStrzalu), random.nextInt(95) + 5, gracz2, gracz1);
    }


}
