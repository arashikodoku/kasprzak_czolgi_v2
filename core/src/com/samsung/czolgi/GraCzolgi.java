package com.samsung.czolgi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraCzolgi extends ApplicationAdapter {
	SpriteBatch batch;

    private Pocisk pocisk;


	
	@Override
	public void create () {
		batch = new SpriteBatch();


        stworzPocisk();
	}

    private void stworzPocisk() {
        Texture pociskTexture = new Texture("pocisk.png");
        pocisk = new Pocisk(pociskTexture);
        pocisk.set(100, 100, Pocisk.WIDTH, Pocisk.HEIGHT);
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		batch.begin();

        batch.draw(pocisk.getTexture(), pocisk.x, pocisk.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		pocisk.getTexture().dispose();
	}
}
