package com.wizardshapes.ashley.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wizardshapes.ashley.AshleyTest;
import com.wizardshapes.ashley.Assets;
import com.wizardshapes.ashley.components.AnimationComponent;
import com.wizardshapes.ashley.components.MovementComponent;
import com.wizardshapes.ashley.components.PlayerComponent;
import com.wizardshapes.ashley.components.StateComponent;
import com.wizardshapes.ashley.components.TextureComponent;
import com.wizardshapes.ashley.components.TransformComponent;
import com.wizardshapes.ashley.systems.AnimationSystem;
import com.wizardshapes.ashley.systems.MovementSystem;
import com.wizardshapes.ashley.systems.PlayerSystem;
import com.wizardshapes.ashley.systems.RenderingSystem;
import com.wizardshapes.ashley.systems.StateSystem;

public class GameScreen extends ScreenAdapter {
	AshleyTest game;

	OrthographicCamera guiCam;
	Engine engine;
	
	public GameScreen (AshleyTest game) {
		this.game = game;
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		engine = new Engine();
		
		engine.addSystem(new PlayerSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new StateSystem());
		engine.addSystem(new RenderingSystem(game.batch));
		
		Entity bob = createBob();
	}
	
	@Override
	public void render (float delta) {
		engine.update(delta);
		
		ApplicationType appType = Gdx.app.getType();
		
		// should work also with Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
		float accelX = 0.0f;
		
		if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) accelX = 2f;
		if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) accelX = -2f;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) accelX *= PlayerComponent.SPRINT_MULTIPLIER;
		
		engine.getSystem(PlayerSystem.class).setAccelX(accelX);
		
	}
	
	private Entity createBob() {
		Entity entity = new Entity();

		PlayerComponent bob = new PlayerComponent();
		MovementComponent movement = new MovementComponent();
		TransformComponent position = new TransformComponent();
		TextureComponent texture = new TextureComponent();
		StateComponent state = new StateComponent();
 		AnimationComponent animation = new AnimationComponent();
		//texture.region = Assets.playerIdle;
 		animation.animations.put(PlayerComponent.STATE_IDLE, Assets.idleAnimation);
 		animation.animations.put(PlayerComponent.STATE_JOGGING, Assets.jogAnimation);
 		animation.animations.put(PlayerComponent.STATE_RUNNING, Assets.runAnimation);
		position.pos.set(5.0f, 1.0f, 0.0f);
		
		entity.add(bob);
		entity.add(movement);
		entity.add(position);
		entity.add(texture);
		entity.add(animation);
		entity.add(state);
		
		engine.addEntity(entity);
		
		return entity;
	}
}
