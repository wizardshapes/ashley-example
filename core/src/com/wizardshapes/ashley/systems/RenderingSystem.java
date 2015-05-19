package com.wizardshapes.ashley.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.wizardshapes.ashley.components.MapLayerComponent;
import com.wizardshapes.ashley.components.TextureComponent;
import com.wizardshapes.ashley.components.TransformComponent;

public class RenderingSystem extends IteratingSystem {
	
	static final float FRUSTUM_WIDTH = 30;
	static final float FRUSTUM_HEIGHT = 20;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
	private SpriteBatch batch;
	private Array<Entity> backgroundQueue;
	private Array<Entity> foregroundQueue;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;
	private OrthographicCamera cam;
	private BatchTiledMapRenderer tiledMapRenderer;
	
	private ComponentMapper<TextureComponent> textureMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<MapLayerComponent> mapLayerMapper;
	private Comparator<Entity> mapComparator;

	public RenderingSystem(SpriteBatch batch, BatchTiledMapRenderer tiledMapRenderer) {
		super(Family.one(TransformComponent.class, TextureComponent.class, MapLayerComponent.class).get());
		
		textureMapper = ComponentMapper.getFor(TextureComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		mapLayerMapper = ComponentMapper.getFor(MapLayerComponent.class);
		backgroundQueue = new Array<Entity>();
		foregroundQueue = new Array<Entity>();
		renderQueue = new Array<Entity>();
		
		comparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int)Math.signum(transformMapper.get(entityB).pos.z -
										transformMapper.get(entityA).pos.z);
			}
		};
		
		mapComparator = new Comparator<Entity>(){
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int)Math.signum(mapLayerMapper.get(entityB).zIndex -
										mapLayerMapper.get(entityA).zIndex);
			}
		};
		
		this.batch = batch;
		this.tiledMapRenderer = tiledMapRenderer;
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		renderQueue.sort(comparator);
		backgroundQueue.sort(mapComparator);
		foregroundQueue.sort(mapComparator);
		
		cam.update();
		
		//quick and dirty camera test
//		ShapeRenderer shapeRenderer = new ShapeRenderer();
//		shapeRenderer.setProjectionMatrix(cam.combined);
//		shapeRenderer.begin(ShapeType.Filled);
//		for (float i = -1000; i <= 100; i += 10){
//			
//			shapeRenderer.setColor(Color.ORANGE);
//			shapeRenderer.circle(i, 10f, 1);
//		}
//		shapeRenderer.end();
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.getBatch().setProjectionMatrix(cam.combined);
		for(Entity entity : backgroundQueue){
			System.out.println("Printing background");
			renderMapLayer(entity);
		}
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		
		for (Entity entity : renderQueue) {
			TextureComponent tex = textureMapper.get(entity);
			
			if (tex.region == null) {
				continue;
			}
			
			TransformComponent t = transformMapper.get(entity);
		
			float width = tex.region.getRegionWidth();
			float height = tex.region.getRegionHeight();
			float originX = width * 0.5f;
			float originY = height * 0.5f;
			
			batch.draw(tex.region,
					   t.pos.x - originX, t.pos.y - originY,
					   originX, originY,
					   width, height,
					   t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES,
					   MathUtils.radiansToDegrees * t.rotation);
		}
		
		
		
		batch.end();
		renderQueue.clear();
		
		
		for(Entity entity : foregroundQueue){
			System.out.println("Printing foreground");
			renderMapLayer(entity);
		}
		foregroundQueue.clear();
		backgroundQueue.clear();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		System.err.println(entity.getComponents());
		if(mapLayerMapper.has(entity)){
			System.err.println("Found Map");
			MapLayerComponent layer = mapLayerMapper.get(entity);
			if(layer.zIndex >= 0)
				foregroundQueue.add(entity);
			else
				backgroundQueue.add(entity);
		}else{
			renderQueue.add(entity);
		}
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	private void renderMapLayer(Entity entity){
		tiledMapRenderer.getBatch().begin();
		MapLayerComponent layer = mapLayerMapper.get(entity);
		if(layer.layer == null){
			System.err.println("Null map Layer");
			return;
		}
		tiledMapRenderer.renderTileLayer(layer.layer);
		tiledMapRenderer.getBatch().end();
	}

}
