package ui.screens;

import static game.AgentSimulatorConstants.screenHeight;
import static game.AgentSimulatorConstants.screenWidth;
import factorys.AgentFactory;
import factorys.ObstacleFactory;
import fileReader.SaveFileReader;
import game.systems.SystemsManager;

import java.io.File;

import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentType;
import agentDefinitions.AgentWorld;
import agentDefinitions.Obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class AgentSimulationScreen implements Screen {

	private AgentWorld world;
	private OrthographicCamera camera;
	private SystemsManager systemsManager;
	private SaveFileReader saveLoader;

	public AgentSimulationScreen(File mapFile, int scale) {
		this.world = new AgentWorld();
		this.camera = new OrthographicCamera(screenWidth, screenHeight);
		this.systemsManager= new SystemsManager(world, camera);
		try {
			this.saveLoader = new SaveFileReader(mapFile, world);
		} catch (Error e) {
			System.out.println(e);
		}
		// System.out.println(saveLoader.readNextLine());
		// System.out.println(saveLoader.getTextFile() == null);
		saveLoader.setScaleFactor(scale);
		saveLoader.loadObstacles();
		// creatBoxBoarder();
		addAgents(AgentType.PERSUER, 100);
		addAgents(AgentType.EVADER, 100);
	}

	private void addAgents(AgentType type, int count) {
		AgentFactory factory = new AgentFactory(world.getPhysicsWorld());
		for (int i = 1; i <= count; i++) {
			AbstractAgent agent = factory.createAgent(new Vector2(300, 150), type);
			world.addAgent(agent);
		}

	}



	public void show() {

	}

	private void creatBoxBoarder() {
		ObstacleFactory obstacleFactory = new ObstacleFactory(world.getPhysicsWorld());
		Obstacles rightBoarder = obstacleFactory.createObstacle(new Vector2[] { new Vector2(), new Vector2(10, 0),
				new Vector2(10, 300), new Vector2(0, 300) }, new Vector2(0, 0));
		world.addObstacle(rightBoarder);
		Obstacles leftBoarder = obstacleFactory.createObstacle(new Vector2[] { new Vector2(), new Vector2(10, 0),
				new Vector2(10, 300), new Vector2(0, 300) }, new Vector2(600, 0));
		world.addObstacle(leftBoarder);
		Obstacles bottomBoarder = obstacleFactory.createObstacle(new Vector2[] { new Vector2(), new Vector2(600, 0),
				new Vector2(600, 10), new Vector2(0, 10) }, new Vector2(0, 0));
		world.addObstacle(bottomBoarder);
		Obstacles topBoarder = obstacleFactory.createObstacle(new Vector2[] { new Vector2(), new Vector2(600, 0),
				new Vector2(600, 10), new Vector2(0, 10) }, new Vector2(0, 300));
		world.addObstacle(topBoarder);
		
	}

	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		systemsManager.runSystemStep(delta);
		// world.process(delta);

	}

	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	

	public void hide() {
		// TODO Auto-generated method stub
		
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void resume() {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
