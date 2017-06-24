package game;


public class AgentSimulatorConstants {

	public static final int agentScreenWidth = 1350;

	public static final int agentScreenHeight = 700;

	public static final short GROUP_Agents = -1;

	public static final short GROUP_Obstacles = -2;

	// radius of agent sight
	public static final float detectionRadius = 100;
	// if <= this range evaders get removed
	public static final int deathRadius = 25;

	// time in milliseconds
	public static final int aiDecisionsUpdate = 500;
	// Agent Velocity
	public static final int maxVelosity = 20;

	public static final int maxVelosityEvaders = 20;

	// public static final int obsDetectionRadius = 100;
}
