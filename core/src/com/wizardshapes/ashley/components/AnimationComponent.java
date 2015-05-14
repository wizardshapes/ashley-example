package com.wizardshapes.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent extends Component {
	public IntMap<Animation> animations = new IntMap<Animation>();
}
