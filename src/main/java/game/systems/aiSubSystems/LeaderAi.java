package game.systems.aiSubSystems;

import game.AgentSimulatorConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentState;
import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;
import agentDefinitions.PersuerAgent;

import com.badlogic.gdx.math.Vector2;

public class LeaderAi {
	
	
	private int deathDistance;

	private DetectionSystem detectionSystem;
	private long startOfSim;
	private final long targetUpdateRate = AgentSimulatorConstants.aiDecisionsUpdate;
	private ObstacleEvasionCalculator obstacleEvasionCalculator;


	public LeaderAi() {
		super();
		this.detectionSystem = new DetectionSystem();
		startOfSim = System.currentTimeMillis();
		deathDistance = AgentSimulatorConstants.deathRadius;
		this.obstacleEvasionCalculator = new ObstacleEvasionCalculator();
	}
	
	public void runLeaderAi(AgentWorld world) {
		if (isTimeForUpdate()) {
			// System.out.println("ai running");
			ArrayList<ArrayList<AbstractAgent>> detectionList = detectionSystem.detectAllAgents(world);
			AiHeatMap heatMap = new AiHeatMap(100, 100, world);
			HashSet<EvaderAgent> mapedAgents = new HashSet<EvaderAgent>();
			heatMap.markObstacles(world);

			ArrayList<PersuerAgent> persuerList = new ArrayList<PersuerAgent>();
			HashMap<PersuerAgent, Double> distanceList = new HashMap<PersuerAgent, Double>();

			for (int i = 0; i < world.getAllAgents().size(); i++) {
				AbstractAgent agent = world.getAllAgents().get(i);
				ArrayList<AbstractAgent> detectedAgents = detectionList.get(i);
				detectedAgents.remove(agent);

				if (agent.getClass() == PersuerAgent.class) {
					persuerList.add((PersuerAgent) agent);
					EvaderAgent closestEvaderAgent = findClosestEvader(agent.getPossition(), detectedAgents);

					double distance;
					if (closestEvaderAgent != null) {
						distance = agent.getPossition().cpy().dst(closestEvaderAgent.getPossition().cpy());
					} else {
						distance = Double.POSITIVE_INFINITY;
					}
					mapEvaders(heatMap, detectedAgents, mapedAgents);
					distanceList.put((PersuerAgent) agent, distance);

				}
				// heatMap.printHeatMap();
			}
			sortAgents(persuerList, distanceList);
			ArrayList<Vector2> targetList = new ArrayList<Vector2>();
			// persuit state
			if (mapedAgents.size() > 0) {
				for (int k = 0; k < persuerList.size(); k++) {
					PersuerAgent persuer = persuerList.get(k);
					Vector2 targetPosition = heatMap.mostHeatedSpot(persuer);
					targetList.add(targetPosition);
					heatMap.applyCool(targetPosition.cpy());

				}
				for (int n = 0; n < persuerList.size(); n++) {
					PersuerAgent persuer = persuerList.get(n);
					Vector2 dirToClosestTarget = calculateClosestTargetDir(persuer.getPossition().cpy(), targetList);
					persuer.setAgentState(AgentState.PERSUER_PERSUIT);
					Vector2 finalDir = obstacleEvasionCalculator.calculateFinalDirAfterHeatMap(persuer,
							dirToClosestTarget, world);
					persuer.setDirection(finalDir);
				}
				// search state
			} else {
				// System.out.println("runs");
				for (int k = 0; k < persuerList.size(); k++) {
					PersuerAgent persuer = persuerList.get(k);
					Vector2 targetPosition = heatMap.mostHeatedSpot(persuer);
					targetList.add(targetPosition);
					heatMap.applyCool(targetPosition.cpy());

				}
				for (int n = 0; n < persuerList.size(); n++) {
					PersuerAgent persuer = persuerList.get(n);
					Vector2 dirToClosestTarget = calculateClosestTargetDir(persuer.getPossition().cpy(), targetList);
					persuer.setAgentState(AgentState.PERSUER_SEARCH);
					persuer.setDirection(dirToClosestTarget);
				}
			}
			startOfSim = System.currentTimeMillis();
			
		}
	}

	private Vector2 calculateClosestTargetDir(Vector2 pos, ArrayList<Vector2> targetList) {
		Vector2 bestDir = new Vector2(Float.MAX_VALUE,Float.MAX_VALUE);
		Vector2 bestTarget = null;
		for (int k = 0; k < targetList.size(); k++) {
			Vector2 distance = targetList.get(k).cpy().sub(pos.cpy());
			if (distance.len() < bestDir.len()) {
				bestDir = distance;	
				bestTarget = targetList.get(k);
			}
		}
		targetList.remove(bestTarget);
		return bestDir;
	}

	private void sortAgents(ArrayList<PersuerAgent> persuerList, HashMap<PersuerAgent, Double> distanceList) {
		Comparator<PersuerAgent> ClosestDistanceComparator = new Comparator<PersuerAgent>() {

			@Override
			public int compare(PersuerAgent o1, PersuerAgent o2) {
				return (int) Math.signum(distanceList.get(o1) - distanceList.get(o2));
			}
		};
		Collections.sort(persuerList, ClosestDistanceComparator);

	}

	private void mapEvaders(AiHeatMap heatMap, ArrayList<AbstractAgent> detectedAgents, HashSet<EvaderAgent> mapedAgents) {

		for(int j =0;j<detectedAgents.size();j++){
			if (detectedAgents.get(j).getClass() == EvaderAgent.class) {

				if (!mapedAgents.contains(detectedAgents.get(j))) {
				heatMap.mapAgent(detectedAgents.get(j));
				mapedAgents.add((EvaderAgent) detectedAgents.get(j));
				}
			}

		}

	}
	private boolean isTimeForUpdate() {
		return trackTimeElapsed() >= targetUpdateRate;
	}

	private int trackTimeElapsed() {
		return (int) (System.currentTimeMillis() - startOfSim);
	}

	private EvaderAgent findClosestEvader(Vector2 position, ArrayList<AbstractAgent> detectedAgents) {
		Vector2 closestDistance = new Vector2();
		EvaderAgent returnAgent = null;
		boolean evaderDetected = false;

		for (int j = 0; j < detectedAgents.size(); j++) {

			if (detectedAgents.get(j).getClass() == EvaderAgent.class) {
				if (evaderDetected == false) {
					// System.out.println("detection");
					Vector2 distance = position.cpy().sub(detectedAgents.get(j).getPossition().cpy());
					closestDistance = distance.cpy();
					returnAgent = (EvaderAgent) detectedAgents.get(j);
					evaderDetected = true;

				} else {

					Vector2 distance = position.cpy().sub(detectedAgents.get(j).getPossition().cpy());

					if (distance.len() < closestDistance.len()) {
						returnAgent = (EvaderAgent) detectedAgents.get(j);
						closestDistance = distance;

					}

				}

			}

		}

		return returnAgent;
	}

	
	

}
