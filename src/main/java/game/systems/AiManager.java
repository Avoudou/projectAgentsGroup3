package game.systems;

import game.systems.abstractDef.AbstractSystem;
import game.systems.aiSubSystems.LeaderAi;
import game.systems.aiSubSystems.MainEvaderAi;
import game.systems.aiSubSystems.RandomAi;
import game.systems.aiSubSystems.SwarmAi;
import agentDefinitions.AgentWorld;

public class AiManager extends AbstractSystem {

	private RandomAi randomAi;
	private SwarmAi basicSwarmAi;
	private MainEvaderAi evaderAi;
	private LeaderAi leaderAi;

	public AiManager() {
		super();
		this.randomAi = new RandomAi();
		this.basicSwarmAi = new SwarmAi();
		this.evaderAi = new MainEvaderAi();
		this.leaderAi = new LeaderAi();
	}

	@Override
	public void proccessStep(AgentWorld world, float delta) {
		// randomAi.proccessStep(world, delta);
		// basicSwarmAi.runSwarmAi(world);
		evaderAi.runEvasionAi(world);
		leaderAi.runLeaderAi(world);

	}

}
