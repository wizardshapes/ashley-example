package com.wizardshapes.ashley.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent extends Component {
	public static final int STATE_IDLE = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_JOGGING = 2;
	public static final int STATE_AIRBORNE = 3;
	public static final float JUMP_VELOCITY = 11;
	public static final float MOVE_VELOCITY = 20;
	public static final float WIDTH = 2f;
	public static final float HEIGHT = 2f;
	public static final float SPRINT_MULTIPLIER = 2f;
}
