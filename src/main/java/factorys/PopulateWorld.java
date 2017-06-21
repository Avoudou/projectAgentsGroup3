package factorys;

import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentType;
import agentDefinitions.AgentWorld;

import com.badlogic.gdx.math.Vector2;

public class PopulateWorld {


	public static void addAllAgents(AgentWorld world) {
		addGroupedAgents(world, AgentType.PERSUER, 2, 1, 0, 0);
		// addGroupedAgents(world, AgentType.EVADER, 10, 0, 0, 200);
		addGroupedAgents(world, AgentType.EVADER, 1, 0, 0, +125);
		addGroupedAgents(world, AgentType.EVADER, 1, 0, 0, -125);

	}

	private static void addGroupedAgents(AgentWorld world, AgentType type, int count,int numberOfGroups, int x, int y) {
		AgentFactory factory = new AgentFactory(world.getPhysicsWorld(), world.getIdMap());
		if (type.equals(type.EVADER)) {
			for (int i = 1; i <= count; i++) {
				AbstractAgent agent = factory.createAgent(new Vector2(x, y), type);
				world.addAgent(agent);

			}
		}
		
		else if (type.equals(type.PERSUER)){
			int agentGroup= 0;
			for (int j=1; j<=numberOfGroups;j++){
							
			agentGroup++;
			
			for (int i=1; i<=count/numberOfGroups;i++){
				AbstractAgent agent = factory.createAgent(new Vector2(x, y), type);
				agent.setAgentGroup(agentGroup);
				world.addAgent(agent);
				System.out.println(agent.getAgentGroup());
				}
			}
		}
		

	}
}
