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

	@Override
	public void create () {
		batch = new SpriteBatch();

		music = Gdx.audio.newMusic(Gdx.files.internal("XX.mp3"));
		music.setLooping(true);
		music.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 800);

        stworzPocisk();
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
        batch.draw(pocisk.getTexture(), pocisk.x, pocisk.y);
		batch.end();
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
}
