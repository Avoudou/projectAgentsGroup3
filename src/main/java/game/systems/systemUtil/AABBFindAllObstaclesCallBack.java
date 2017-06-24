package game.systems.systemUtil;

import java.util.ArrayList;

import agentDefinitions.AgentWorld;
import agentDefinitions.Obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

// TRY OUT CLASS // ISNT USED
public class AABBFindAllObstaclesCallBack implements QueryCallback {

	/** The game world, used for entity retrieval in order to pass them to the predicate */
	private AgentWorld world;

	/** The center of the querying rectangle, the dimensions are specified in the query call itself */
	private Vector2 origin;

	/** The radius for the search (this is needed because the query is run on a rectangle) */
	private float radius;

	private ArrayList<Obstacles> detectedObstacles;

	public AABBFindAllObstaclesCallBack(AgentWorld world, Vector2 origin, float radius) {
		this.world = world;
		this.origin = origin;
		this.radius = radius;
		detectedObstacles = new ArrayList<Obstacles>();
	}

	@Override
	public boolean reportFixture(Fixture fixture) {
		Integer obstacleId = (Integer) fixture.getUserData();
		if (obstacleId > 0) {
			// Fixture was not an obstacle
			return true;
		}

		Obstacles obs = world.getObstacleIdMap().get(obstacleId);
		// Vector2 position = obs.getPhysicsBody().getPosition();
		// if (position.dst(origin) < radius) {
			detectedObstacles.add(obs);
		// }
		// System.out.println(detectedObstacles.size());
		return true;
	}

	public ArrayList<Obstacles> getDetectedObstacles() {
		return detectedObstacles;
	}
}
