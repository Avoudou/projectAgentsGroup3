package factorys;

import game.AgentSimulatorConstants;

import java.util.HashMap;

import ui.rendering.RenderComponent;
import agentDefinitions.Obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ObstacleFactory {
	private World physicsWorld;
	private static int obstaclesIdNumber = 1;
	private HashMap<Integer, Obstacles> idMap;

	public ObstacleFactory(World physicsWorld, HashMap<Integer, Obstacles> idMap) {
		super();
		this.physicsWorld = physicsWorld;
		this.idMap = idMap;
	}

	public Obstacles createObstacle(Vector2[] listOfVertices, Vector2 position) {
		BodyDef obstacDef = new BodyDef();
		obstacDef.type = BodyType.StaticBody;
		// Set its world position
		obstacDef.position.set(position);

		// Create a body from the defintion and add it to the world
		Body obstacleBody = physicsWorld.createBody(obstacDef);

		// Create a polygon shape
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape obstacleShape = new PolygonShape();
		obstacleShape.set(listOfVertices);
		fixtureDef.shape = obstacleShape;
		fixtureDef.filter.groupIndex = AgentSimulatorConstants.GROUP_Obstacles;
		obstacleBody.createFixture(fixtureDef);
		// boolean isObstacle = true;
		obstacleBody.getFixtureList().get(0).setUserData(new Integer(-obstaclesIdNumber));
		// Clean up after ourselves
		obstacleShape.dispose();

		RenderComponent renderComp = new RenderComponent(obstacleBody, null);

		Obstacles obstacle = new Obstacles(obstacleBody, renderComp, listOfVertices);
		Integer id = (Integer) obstacleBody.getUserData();
		idMap.put(id, obstacle);
		obstaclesIdNumber++;
		return obstacle;

	}

}
