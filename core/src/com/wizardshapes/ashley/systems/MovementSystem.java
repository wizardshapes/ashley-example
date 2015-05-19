package com.wizardshapes.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wizardshapes.ashley.components.MovementComponent;
import com.wizardshapes.ashley.components.TransformComponent;

public class MovementSystem extends IteratingSystem {

	private Vector2 tmp = new Vector2();

	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<MovementComponent> mm;
	
	public MovementSystem() {
		super(Family.all(TransformComponent.class, MovementComponent.class).get());
		
		tm = ComponentMapper.getFor(TransformComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		TransformComponent pos = tm.get(entity);
		MovementComponent mov = mm.get(entity);;
		
		tmp.set(mov.accel).scl(deltaTime);
		mov.velocity.add(tmp);
		
		tmp.set(mov.velocity).scl(deltaTime);
		pos.pos.add(tmp.x, tmp.y, 0.0f);
		
		//System.out.println(mov.velocity.x );
		if(mov.velocity.x > 0.0f){
			pos.scale.x = Math.abs(pos.scale.x);
			pos.scale.x *= 1.0f;
		}else if(mov.velocity.x < 0.0f){
			pos.scale.x = Math.abs(pos.scale.x);
			pos.scale.x *= -1.0f;
		}
	}

}
