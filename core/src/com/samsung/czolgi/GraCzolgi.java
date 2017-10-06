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

public class GraCzolgi extends ApplicationAdapter {
	private SpriteBatch batch;
	private Music music;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

	private Pocisk pocisk;


    private boolean pociskLeci;

	// zasoby
    static Texture czolgTex;
	static Texture wiezyczkaTex;

	@Override
	public void create () {
		batch = new SpriteBatch();

		music = Gdx.audio.newMusic(Gdx.files.internal("XX.mp3"));
		music.setLooping(true);
		music.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 800);

        stworzPocisk();

        czolgTex = new Texture("tank.png");
		wiezyczkaTex = new Texture("turret.png");
	}

    private void stworzPocisk() {
        Texture pociskTexture = new Texture("pocisk.png");
        pocisk = new Pocisk(pociskTexture);
        pocisk.set(100, 100, Pocisk.WIDTH, Pocisk.HEIGHT);
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		rysujZiemie();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

        wystrzelPocisk();

        if(pociskLeci) {

            batch.draw(pocisk.getTexture(), pocisk.x, pocisk.y);
        }

        batch.draw(pocisk.getTexture(), pocisk.x, pocisk.y);

        new Czolg().draw(batch);
		batch.end();

        przesunPocisk(100, 10);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		pocisk.getTexture().dispose();
	}

	private void rysujZiemie() {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BROWN);
		shapeRenderer.rect(0, 0, 1600, 200);
		shapeRenderer.end();
	}


    private void wystrzelPocisk() {
        pociskLeci = true;
    }

    private void usunPocisk() {
        pociskLeci = false;
    }

    private void przesunPocisk(int x, int y) {
        pocisk.setX(pocisk.x + x*Gdx.graphics.getDeltaTime());
        pocisk.setY(pocisk.y + y * Gdx.graphics.getDeltaTime());
    }

}
