package com.wizardshapes.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wizardshapes.ashley.components.CameraComponent;
import com.wizardshapes.ashley.components.TransformComponent;

public class CameraSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<CameraComponent> cm;
	
	public CameraSystem() {
		super(Family.all(CameraComponent.class).get());
		
		tm = ComponentMapper.getFor(TransformComponent.class);
		cm = ComponentMapper.getFor(CameraComponent.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		CameraComponent cam = cm.get(entity);
		
		if (cam.target == null) {
			return;
		}
		
		TransformComponent target = tm.get(cam.target);
		
		if (target == null) {
			return;
		}

		if ((target.pos.x - cam.camera.position.x) > (cam.xBuffer)) 
			cam.camera.position.x = (target.pos.x - cam.xBuffer);
		if ((target.pos.x - cam.camera.position.x) < (-cam.xBuffer))                 
			cam.camera.position.x = (target.pos.x + cam.xBuffer);
		if ((target.pos.y - cam.camera.position.y) > (cam.yBuffer)) 
			cam.camera.position.y = (target.pos.y - cam.yBuffer);
		if ((target.pos.y - cam.camera.position.y) < (-cam.yBuffer))                 
			cam.camera.position.y = (target.pos.y + cam.yBuffer);
		
		
		//cam.camera.position.x = Math.max(cam.camera.position.x, target.pos.x);
		//cam.camera.position.y = Math.max(cam.camera.position.y, target.pos.y);
	}
}
