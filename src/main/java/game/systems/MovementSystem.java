package game.systems;

import game.AgentSimulatorConstants;
import game.systems.abstractDef.AbstractSystem;
import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;

import com.badlogic.gdx.math.Vector2;

public class MovementSystem extends AbstractSystem {

	private int maxVelosity = 0;
	private int maxVelocityEvaders = 0;

	private long startOfSim;
	private final long movementUpdateRateMillsec = 20;

	public MovementSystem() {
		startOfSim = System.currentTimeMillis();
		this.maxVelosity = AgentSimulatorConstants.maxVelosity;
		this.maxVelocityEvaders = AgentSimulatorConstants.maxVelosityEvaders;
	}

	@Override
	public void proccessStep(AgentWorld world, float delta) {
		if (isTimeForUpdate()) {
	
			for (int i = 0; i < world.getAllAgents().size(); i++) {
				moveAgent(world.getAllAgents().get(i));
			}
			startOfSim = System.currentTimeMillis();
		}
	}

	public void moveAgent(AbstractAgent agent) {
		if (agent.getDirection() != null) {
			Vector2 velocity = agent.getDirection().cpy().nor();

			if (agent.getClass() == EvaderAgent.class) {
				agent.getPhysicsBody().setLinearVelocity(velocity.scl(AgentSimulatorConstants.maxVelosityEvaders));
			} else {
				agent.getPhysicsBody().setLinearVelocity(velocity.scl(AgentSimulatorConstants.maxVelosity));
			}

		}

	}

	

	private boolean isTimeForUpdate() {
		return trackTimeElapsed() >= movementUpdateRateMillsec;
	}

	private int trackTimeElapsed() {
		return (int) (System.currentTimeMillis() - startOfSim);
	}

}
