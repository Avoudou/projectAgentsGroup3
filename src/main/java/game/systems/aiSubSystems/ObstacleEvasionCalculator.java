package game.systems.aiSubSystems;

import game.systems.systemUtil.AgentRayCastObstacles;

import java.util.ArrayList;

import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentWorld;

import com.badlogic.gdx.math.Vector2;

public class ObstacleEvasionCalculator {
	private ArrayList<Vector2> dirList;
	private int obstacleDetRAdius = 100;

	public ObstacleEvasionCalculator() {
		dirList = new ArrayList<Vector2>();
		Vector2 rayCastDir1 = new Vector2(0, 1);
		Vector2 rayCastDir2 = new Vector2(1, 0);
		Vector2 rayCastDir3 = new Vector2(0, -1);
		Vector2 rayCastDir4 = new Vector2(-1, 0);
		dirList.add(rayCastDir1);
		dirList.add(rayCastDir2);
		dirList.add(rayCastDir3);
		dirList.add(rayCastDir4);

	}

	public Vector2 calculateComp(AbstractAgent agent, AgentWorld world) {


		// System.out.println("start obs avoid calc");
		Vector2 ret = new Vector2();
		for (int i = 0; i < 4; i++) {



		Vector2 startPoint = agent.getPossition().cpy().add(new Vector2((float) 0.1,(float) 0.1));

			Vector2 endPoint = startPoint.cpy().add((dirList.get(i).cpy()).scl(obstacleDetRAdius));
			AgentRayCastObstacles rayCast = new AgentRayCastObstacles(world);

			world.getPhysicsWorld().rayCast(rayCast, startPoint, endPoint);

			if (rayCast.getDetectedObstacle() == null) {
				// System.out.println("no obst");
				// System.out.println();
			} else {
				// System.out.println("maybe here");
				Vector2 agentToColision = (agent.getPossition().cpy().sub(rayCast.getDetectedObstacle().cpy()));
				if (agentToColision.len() <= obstacleDetRAdius) {
					Vector2 addComponent = agentToColision.scl(obstacleDetRAdius / agentToColision.len());
					ret = ret.cpy().add(addComponent);
					// System.out.println("avoidance detected " + dirList.get(i));
				}
			}
		}
		// System.out.println(ret.len());
		return ret.nor().scl(1);
	}

}
