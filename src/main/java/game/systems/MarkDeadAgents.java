package game.systems;

import game.AgentSimulatorConstants;
import game.systems.abstractDef.AbstractSystem;

import java.util.ArrayList;

import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;
import agentDefinitions.PersuerAgent;

public class MarkDeadAgents extends AbstractSystem {
	private long startOfSim;
	private final long targetUpdateRate = AgentSimulatorConstants.aiDecisionsUpdate;

	@Override
	public void proccessStep(AgentWorld world, float delta) {
		if (true) {
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
		
			for (int k = 0; k < persuerList.size(); k++) {
			PersuerAgent persuer = persuerList.get(k);
				for (int l = 0; l < evadArrayList.size(); l++) {
				EvaderAgent evader = evadArrayList.get(l);
					if (evader.getPossition().cpy().dst(persuer.getPossition().cpy()) < AgentSimulatorConstants.deathRadius) {
					evader.setDead(true);
					break;
				}

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

}
