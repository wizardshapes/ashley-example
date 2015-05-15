package com.wizardshapes.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent extends Component {
	public Entity target;
	public OrthographicCamera camera;
	public float xBuffer;
	public float yBuffer;
}
