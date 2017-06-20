package game.systems.aiSubSystems;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import com.badlogic.gdx.math.Vector2;

import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;
import agentDefinitions.PersuerAgent;
import factorys.AgentFactory;
import factorys.PopulateWorld;
import game.AgentSimulatorConstants;

public class LeaderAi {
	
	
	private int deathDistance;
	
	private long startOfSim;
	private final long targetUpdateRate = AgentSimulatorConstants.aiDecisionsUpdate;
	private ObstacleEvasionCalculator obstacleEvasionCalculator;
	
	private final float seperationRadius = 100;
	
	public LeaderAi() {
	super();
	startOfSim = System.currentTimeMillis();
	deathDistance = AgentSimulatorConstants.deathRadius;
	this.obstacleEvasionCalculator = new ObstacleEvasionCalculator();
		
		
	}
	
	public void runLeaderAi(AgentWorld world) {

		if (isTimeForUpdate()) {

			

			for (int i = 0; i < world.getAllAgents().size() ; i++) {
				
			
				AbstractAgent agent = world.getAllAgents().get(i);
				Vector2 position = agent.getPossition().cpy();
				Vector2 movement = new Vector2(50, 50);
				
									
					if (agent.getClass() == PersuerAgent.class) {
						if(agent.getAgentGroup()==1){
						
							agent.setDirection(movement);
						}
						else if (agent.getAgentGroup()==2){
							agent.setDirection(movement.scl(-1));
							
						}
						
						else if (agent.getAgentGroup()==3){
							agent.setDirection(movement.scl(-1,1));
						
						}
						
						else if (agent.getAgentGroup()==4){
							agent.setDirection(movement.scl(1,-1));
						}
					}
						else if (agent.getAgentGroup()==5){
							agent.setDirection(movement.scl(5));
					}
				}
			}
		}
	
	/*private AbstractAgent evaderHeatMap(Vector2 position, ArrayList<Heatmap> Heatmap) {
		
		EvaderAgent returnAgent = null;
		boolean evaderDetected = false;


		for (int j = 0; j < detectedAgents.size(); j++) {

			if (detectedAgents.get(j).getClass() == EvaderAgent.class) {
				if (evaderDetected == false) {
					//System.out.println("detection");
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
	}*/
	
	/*private AbstractAgent meanGroupLocation (){
		
	}*/
	private boolean isTimeForUpdate(){
		
		return trackTimeElapsed() >= targetUpdateRate;
	}
	
	private int trackTimeElapsed(){
		return (int) (System.currentTimeMillis()- startOfSim);
	}
}
