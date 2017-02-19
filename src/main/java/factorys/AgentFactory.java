package factorys;

import game.AgentSimulatorConstants;
import ui.rendering.RenderComponent;
import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentType;
import agentDefinitions.PersuerAgent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class AgentFactory {
	private World physicsWorld;

	public AgentFactory(World physicsWorld) {
		super();
		this.physicsWorld = physicsWorld;
	}

	public AbstractAgent createAgent(Vector2 position, AgentType type) {
		BodyDef agentBodyDef = new BodyDef();
		// Set its world position
		agentBodyDef.type = BodyType.DynamicBody;
		agentBodyDef.position.set(position);

		// Create a body from the defintion and add it to the world
		Body agentBody = physicsWorld.createBody(agentBodyDef);

		CircleShape circle = new CircleShape();
		circle.setRadius(5f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f; // Make it bounce a little bit
		fixtureDef.filter.groupIndex = AgentSimulatorConstants.GROUP_Agents;

		// Create our fixture and attach it to the body
		Fixture fixture = agentBody.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();

		RenderComponent renderComp = new RenderComponent(agentBody, null);
		AbstractAgent agent = new AbstractAgent(null, null) {
		};
		if (type == AgentType.PERSUER) {
			agent = new PersuerAgent(agentBody, renderComp);
		}

		return agent;

	}

}
