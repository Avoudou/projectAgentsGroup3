package game.systems;

import factorys.PopulateWorld;
import game.systems.abstractDef.AbstractSystem;
import game.systems.aiSubSystems.CoefficientFactory;
import game.systems.aiSubSystems.EvaderCoefficients;
import game.systems.aiSubSystems.PursuerCoefficients;

import java.util.ArrayList;

import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;

public class ResetSimulationSystem extends AbstractSystem {

	private long simulationStartMs;
	// private int counter=0;

	public static ArrayList<Double> timeTakenPerSimulation = new ArrayList<Double>();

	public ResetSimulationSystem() {
		simulationStartMs = System.currentTimeMillis();
	}

	@Override
	public void proccessStep(AgentWorld world, float delta) {
		if (!anyEvaderLeftAlive(world)) {
			for (int i = 0; i < world.getAllAgents().size(); i++) {
				world.getAllAgents().get(i).setDead(true);

			}
			double timeTaken = (System.currentTimeMillis() - simulationStartMs) / 1000.0;
			timeTakenPerSimulation.add(timeTaken);

			int numRunsPerExperiment = 10;
			if (timeTakenPerSimulation.size() == numRunsPerExperiment) {
				System.out.println(timeTakenPerSimulation);
				setNewParameters();
			}

			PopulateWorld.addAllAgents(world);
			simulationStartMs = System.currentTimeMillis();
		}
	}

	private void setNewParameters() {
		CoefficientFactory.USED_PURSUER_COEFFICIENTS = new PursuerCoefficients();
		CoefficientFactory.USED_EVADER_COEFFICIENTS = new EvaderCoefficients();
	}

	private boolean anyEvaderLeftAlive(AgentWorld world) {
		// Returns if the agent list contains any evader agents
		return world.getAllAgents().stream().anyMatch(a -> a.getClass() == EvaderAgent.class);
	}

}
