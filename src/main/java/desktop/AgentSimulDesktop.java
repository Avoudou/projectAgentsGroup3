package desktop;

import static game.AgentSimulatorConstants.agentScreenHeight;
import static game.AgentSimulatorConstants.agentScreenWidth;
import game.AgentSimulator;

import java.io.File;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class AgentSimulDesktop {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = agentScreenWidth;
		config.height = agentScreenHeight;




		AgentSimulator agentSim = new AgentSimulator(new File("savedmaps/MainTestA.txt"), 120);

		LwjglApplication app = new LwjglApplication(agentSim, config);

	

	}

}
