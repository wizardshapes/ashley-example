package com.wizardshapes.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.wizardshapes.ashley.components.AnimationComponent;
import com.wizardshapes.ashley.components.StateComponent;
import com.wizardshapes.ashley.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

	private ComponentMapper<TextureComponent> tm;
	private ComponentMapper<AnimationComponent> am;
	private ComponentMapper<StateComponent> sm;
	
	public AnimationSystem() {
		super(Family.all(TextureComponent.class,
							AnimationComponent.class,
							StateComponent.class).get());
		
		tm = ComponentMapper.getFor(TextureComponent.class);
		am = ComponentMapper.getFor(AnimationComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		long id = entity.getId();
		TextureComponent tex = tm.get(entity);
		AnimationComponent anim = am.get(entity);
		StateComponent state = sm.get(entity);
		
		Animation animation = anim.animations.get(state.get());
		//System.out.println("Time: " + state.time + " delta: " + deltaTime + " state: " + state.get());
		if (animation != null) {
			tex.region = animation.getKeyFrame(state.time); 
		}
	}

}
