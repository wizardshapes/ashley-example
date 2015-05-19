package com.wizardshapes.ashley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public static TextureRegion playerIdle;
	public static Animation jogAnimation;
	public static Animation idleAnimation;
	public static Animation runAnimation;
	public static Music musicTest;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load () {
		Texture jog = loadTexture("jog.png");
		Texture run = loadTexture("run.png");
		playerIdle = new TextureRegion(loadTexture("idle.png"));
		jogAnimation = new Animation(0.12f, new TextureRegion(jog, 0,0,64,64),
				new TextureRegion(jog, 64,0,64,64),
				new TextureRegion(jog, 128,0,64,64),
				new TextureRegion(jog, 192,0,64,64),
				new TextureRegion(jog, 256,0,64,64),
				new TextureRegion(jog, 320,0,64,64));
		runAnimation = new Animation(0.12f, new TextureRegion(run, 0,0,64,64),
				new TextureRegion(run, 64,0,64,64),
				new TextureRegion(run, 128,0,64,64),
				new TextureRegion(run, 192,0,64,64),
				new TextureRegion(run, 256,0,64,64),
				new TextureRegion(run, 320,0,64,64),
				new TextureRegion(run, 384,0,64,64),
				new TextureRegion(run, 448,0,64,64));
		idleAnimation = new Animation(1f, new TextureRegion(loadTexture("idle.png"), 0,0,64,64));
		jogAnimation.setPlayMode(PlayMode.LOOP);
		runAnimation.setPlayMode(PlayMode.LOOP);
		musicTest = Gdx.audio.newMusic(Gdx.files.internal("sounds/turks-theme.mp3"));
	}

}
