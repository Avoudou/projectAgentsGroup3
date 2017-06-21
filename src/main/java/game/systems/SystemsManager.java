package game.systems;

import game.systems.abstractDef.AbstractSystem;

import java.util.ArrayList;

import agentDefinitions.AgentWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class SystemsManager {

	private ArrayList<AbstractSystem> systemList;

	private AgentWorld world;

	public SystemsManager(AgentWorld world, OrthographicCamera camera) {
		this.world = world;
		this.systemList= new ArrayList<AbstractSystem>();
		systemList.add(new AiManager());
		systemList.add(new MovementSystem());
		systemList.add(new RenderSystem(camera, world));
		systemList.add(new MarkDeadAgents());
		systemList.add(new RemoveDeadAgents());
		systemList.add(new ResetSimulationSystem());


	}

	public void runSystemStep(float delta) {
		for (int i = 0; i < systemList.size(); i++) {
			systemList.get(i).proccessStep(world, delta);
			world.getPhysicsWorld().step(delta, 8, 3);
		}
	}

}
