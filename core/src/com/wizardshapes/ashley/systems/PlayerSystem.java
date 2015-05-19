package com.wizardshapes.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wizardshapes.ashley.components.MovementComponent;
import com.wizardshapes.ashley.components.PlayerComponent;
import com.wizardshapes.ashley.components.StateComponent;
import com.wizardshapes.ashley.components.TransformComponent;

public class PlayerSystem extends IteratingSystem {
	private static final Family family = Family.all(PlayerComponent.class,
			   TransformComponent.class,
			   MovementComponent.class).get();

	private float accelX = 0.0f;
	
	
	//private ComponentMapper<PlayerComponent> bm;
	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<StateComponent> sm;

	public PlayerSystem() {
		super(family);

		//bm = ComponentMapper.getFor(BobComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}
	
	public void setAccelX(float accelX) {
		this.accelX = accelX;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		accelX = 0.0f;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent t = tm.get(entity);
		MovementComponent mov = mm.get(entity);
		StateComponent state = sm.get(entity);

		if(accelX == 0f && state.get() != PlayerComponent.STATE_IDLE){
			state.set(PlayerComponent.STATE_IDLE);
		}else if (Math.abs(accelX) == 2f && state.get() != PlayerComponent.STATE_JOGGING){
			state.set(PlayerComponent.STATE_JOGGING);
		}else if (Math.abs(accelX) == 2f * PlayerComponent.SPRINT_MULTIPLIER 
				&& state.get() != PlayerComponent.STATE_RUNNING){
			state.set(PlayerComponent.STATE_RUNNING);
		}
		mov.velocity.x = -accelX / 16.0f * PlayerComponent.MOVE_VELOCITY;
	}

}
