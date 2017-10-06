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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

public class GraCzolgi extends ApplicationAdapter {
	private SpriteBatch batch;
	private Music music;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

    private World world;

	private Pocisk pocisk;
    private Texture pociskTexture;

    private boolean pociskLeci;

	// zasoby
    static Texture czolgTex;
	static Texture wiezyczkaTex;

	// Gracze
	Czolg gracz1;
	Czolg gracz2;

	@Override
	public void create () {
		batch = new SpriteBatch();

		czolgTex = new Texture("tank.png");
		wiezyczkaTex = new Texture("turret.png");

		music = Gdx.audio.newMusic(Gdx.files.internal("XX.mp3"));
		music.setLooping(true);
		music.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 800);

        stworzPocisk();
		stworzCzolgi();

        Box2D.init();
        world = new World(new Vector2(0, -10), true);
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
	public void render () {
        stepWorld();
		Gdx.gl.glClearColor(0.5f, 0.8f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		rysujZiemie();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(pocisk.getTexture(), pocisk.x, pocisk.y);
		batch.end();

		DirectionGestureDetector gestureDetector = new DirectionGestureDetector();
		Gdx.input.setInputProcessor(gestureDetector);

		if (Gdx.input.isTouched()) {
			Vector3 pozycja = new Vector3();
			pozycja.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(pozycja);
			pocisk.x = pozycja.x;
			pocisk.y = pozycja.y;
		}

		gracz1.draw(batch);
		gracz2.draw(batch);

        wystrzelPocisk(45, 150, 150 );
        batch.end();

        przesunPocisk(100, 10);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		pocisk.getTexture().dispose();
        world.dispose();
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
        pocisk.draw(batch);
    }

    private void usunPocisk() {
        pociskLeci = false;
    }

    private void przesunPocisk(int x, int y) {

    }

    private void przesunPocisk(float angle, float power) {

    }


    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;

    float accumulator = 0;

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

}
