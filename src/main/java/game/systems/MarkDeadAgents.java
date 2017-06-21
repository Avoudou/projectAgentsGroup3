package game.systems;

import game.AgentSimulatorConstants;
import game.systems.abstractDef.AbstractSystem;

import java.util.ArrayList;

import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;
import agentDefinitions.PersuerAgent;

public class MarkDeadAgents extends AbstractSystem {

	@Override
	public void proccessStep(AgentWorld world, float delta) {
		ArrayList<PersuerAgent> persuerList = new ArrayList<PersuerAgent>();
		ArrayList<EvaderAgent> evadArrayList = new ArrayList<EvaderAgent>();
		for (int i = 0; i < world.getAllAgents().size(); i++) {
			if (world.getAllAgents().get(i).getClass() == PersuerAgent.class) {
				persuerList.add((PersuerAgent) world.getAllAgents().get(i));

			}
			if (world.getAllAgents().get(i).getClass() == EvaderAgent.class) {
				evadArrayList.add((EvaderAgent) world.getAllAgents().get(i));
			}

		}
		
		for (int k = 0; k < evadArrayList.size(); k++) {
			EvaderAgent evader = evadArrayList.get(k);
			for (int l = 0; l < persuerList.size(); l++) {
				PersuerAgent persuer = persuerList.get(l);
				if (evader.getPossition().cpy().dst(persuer.getPossition().cpy()) < AgentSimulatorConstants.deathRadius) {
					evader.setDead(true);
				}

			}

		}

	}

}
