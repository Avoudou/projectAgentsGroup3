package agentDefinitions;

import ui.rendering.RenderComponent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractAgent {

	protected Body physicsBody;
	protected RenderComponent renderData;
	// protected Vector2 targetPosition;
	protected Vector2 direction;
	protected AgentState agentState;
	protected boolean isDead;
	protected int agentGroup;

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public AgentState getAgentState() {
		return agentState;
	}

	public void setAgentState(AgentState agentState) {
		this.agentState = agentState;
	}

	// public Vector2 getTargetPosition() {
	// return targetPosition;
	// }
	//
	// public void setTargetPosition(Vector2 targetPosition) {
	// this.targetPosition = targetPosition;
	// }

	public AbstractAgent(Body physicsBody, RenderComponent renderData) {
		super();
		this.physicsBody = physicsBody;
		this.renderData = renderData;
		this.direction = new Vector2();
	}

	public AbstractAgent(Body physicsBody, RenderComponent renderData, Vector2 direction) {
		super();
		this.physicsBody = physicsBody;
		this.renderData = renderData;
		this.direction = direction;
	}

	public Body getPhysicsBody() {
		return physicsBody;
	}

	public RenderComponent getRenderData() {
		return renderData;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public Vector2 getPossition() {
		return physicsBody.getPosition().cpy();
	}

	public int getAgentGroup() {
		return agentGroup;
	}

	public void setAgentGroup(int agentGroup) {
		this.agentGroup = agentGroup;
	}
	
	

}
