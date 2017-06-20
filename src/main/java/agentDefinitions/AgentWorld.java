package agentDefinitions;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class AgentWorld {
	private ArrayList<AbstractAgent> allAgents;

	private World physicsWorld;

	private HashMap<Integer, AbstractAgent> idMap;

	private HashMap<Integer, Obstacles> obstacleIdMap;

	private ArrayList<Obstacles> obstacList ;

	private boolean isRunning;


	public AgentWorld() {
		obstacList= new ArrayList<Obstacles>();
		allAgents= new ArrayList<AbstractAgent>();
		idMap = new HashMap<Integer, AbstractAgent>();
		obstacleIdMap = new HashMap<Integer, Obstacles>();
		setPhysicsWorld(new World(new Vector2(), true));
		setRunning(true);
		ArrayList agentGroup = new ArrayList<AbstractAgent>();
		

	}


	public void process(float delta) {

		// physicsWorld.step(delta, 8, 3);

	}
	public ArrayList<AbstractAgent> getAllAgents() {
	
		return allAgents;
	}

	public ArrayList<Obstacles> getObstacList() {
		return obstacList;
	}

	public void addAgent(AbstractAgent agent) {
		allAgents.add(agent);

	}

	public void addObstacle(Obstacles obstacle) {
		obstacList.add(obstacle);

	}


	public World getPhysicsWorld() {
		return physicsWorld;
	}

	// debug method
	public void setPhysicsWorld(World physicsWorld) {
		this.physicsWorld = physicsWorld;
	}

	public HashMap<Integer, Obstacles> getObstacleIdMap() {
		return obstacleIdMap;
	}

	public HashMap<Integer, AbstractAgent> getIdMap() {
		return idMap;
	}


	public boolean isRunning() {
		return isRunning;
	}


	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}



}
