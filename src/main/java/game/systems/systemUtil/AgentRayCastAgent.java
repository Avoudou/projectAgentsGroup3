package game.systems.systemUtil;

import agentDefinitions.AgentWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class AgentRayCastAgent implements com.badlogic.gdx.physics.box2d.RayCastCallback {

	private AgentWorld world;
	private float obstacleFraction = 2;
	private float agentFraction;
	private Integer detectedId;

	public AgentRayCastAgent(AgentWorld world, Integer detectedId) {
		this.world = world;
		this.detectedId = detectedId;

	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

		Integer agentId = (Integer) fixture.getUserData();
		if (agentId < 0) {
			if (fraction < obstacleFraction) {
				obstacleFraction = fraction;
			}
			return 1;
		}
		if (detectedId.equals(agentId)) {
			agentFraction = fraction;
			return fraction;
		}

		return -1;

	}

	public boolean getAgentFound() {
		return agentFraction < obstacleFraction;
	}

}