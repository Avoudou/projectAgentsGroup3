package game.systems.aiSubSystems;

import agentDefinitions.AbstractAgent;
import agentDefinitions.AgentWorld;
import agentDefinitions.EvaderAgent;

import com.badlogic.gdx.math.Vector2;


public class AiHeatMap {

	private double[][] heatmap;
	private int unitSize;
	private final int maxHeat = 1000;
	private final int lengthOfaffectedUnits = 6;
	private ObstacleMapper obsMapper;

	public AiHeatMap(int xDim, int yDim, AgentWorld world) {
		heatmap = new double[xDim][yDim];
		unitSize = 20;
		this.obsMapper = new ObstacleMapper();
		obsMapper.mapObstacles(world, heatmap, unitSize);
		for (int i = 0; i < heatmap.length; i++) {
			for (int j = 0; j < heatmap[0].length; j++) {
				heatmap[i][j] = 10 * Math.random();

			}

		}

	}


	public void applyHeat(Vector2 position) {
		int x = (int) (heatmap.length / 2 + (position.cpy().x / unitSize));
		int y = (int) (heatmap.length / 2 + (position.cpy().y / unitSize));

		for (int i = y - lengthOfaffectedUnits; i < y + lengthOfaffectedUnits; i++) {
			for (int j = x - lengthOfaffectedUnits; j < x + lengthOfaffectedUnits; j++) {
				if (i < 0 || j < 0 || i >= heatmap.length || j >= heatmap[0].length) {
					continue;
				}
				int distance = Math.max(Math.abs(y - i), Math.abs(x - j));
				heatmap[i][j] = heatmap[i][j] + (maxHeat / (1.0 * ((distance + 1))));
				// System.out.println();
			}
		}
	}

	public void applyCool(Vector2 position) {
		int x = (int) (heatmap.length / 2 + (position.cpy().x / unitSize));
		int y = (int) (heatmap.length / 2 + (position.cpy().y / unitSize));
		for (int i = y - (lengthOfaffectedUnits / 2); i < y + lengthOfaffectedUnits / 2; i++) {
			for (int j = x - (lengthOfaffectedUnits / 2); j < x + lengthOfaffectedUnits / 2; j++) {
				if (i < 0 || j < 0 || i >= heatmap.length || j >= heatmap[0].length) {
					continue;
				}
				heatmap[i][j] = heatmap[i][j]
 - (maxHeat / (0.95 * ((Math.max(Math.abs(y - i), Math.abs(x - j)) + 2))));

			}
		}
	}


	public Vector2 mostHeatedSpot(AbstractAgent agent) {
		int agentMapXCoord = calculateAgentXCoord(agent);
		int agentMapYCoord = calculateAgentYCoord(agent);
		// int radius = (int) (1.0 * AgentSimulatorConstants.detectionRadius / unitSize);
		int radius = (int) 1000;
		int bestX = 0;
		int bestY = 0;
		int bestDistance = 0;
		double bestValue = Double.NEGATIVE_INFINITY;
		for (int i = agentMapYCoord - radius; i < agentMapYCoord + radius; i++) {
			for (int j = agentMapXCoord - radius; j < agentMapXCoord + radius; j++) {
				if (i < 0 || j < 0 || i >= heatmap.length || j >= heatmap[0].length) {
					continue;
				}
				if (heatmap[i][j] > bestValue) {
					bestX = j;
					bestY = i;
					bestValue = heatmap[i][j];
					bestDistance = Math.max(Math.abs(agentMapXCoord - i), Math.abs(agentMapYCoord - j));
				}
				if (heatmap[i][j] == bestValue) {
					int distance = Math.max(Math.abs(agentMapXCoord - i), Math.abs(agentMapYCoord - j));
					if (distance < bestDistance) {
						bestX = j;
						bestY = i;
						bestValue = heatmap[i][j];
						bestDistance = distance;
					}
				}
			}
	
		}
	
		// System.out.println(bestValue + " " + calculatePosition(bestX, bestY));
		// System.out.println();
		return calculatePosition(bestY, bestX);
	}


	public void mapAgent(AbstractAgent agent) {
	
		if (agent.getClass() == EvaderAgent.class) {
			applyHeat(agent.getPossition());
	
		}
	
	}


	public void markObstacles(AgentWorld world) {
		for (int i = 0; i < heatmap.length; i++) {
			for (int j = 0; j < heatmap[0].length; j++) {
			}

		}
	}

	public void printHeatMap() {
		for (int i = 0; i < heatmap.length; i++) {
			// System.out.println();

			for (int j = 0; j < heatmap[0].length; j++) {
				if (heatmap[i][j] == 1000) {
					System.out.print(heatmap[i][j] + " ");
					System.out.println(calculatePosition(i, j));

				}


			}

		}
	}

	public int getUnitSize() {
		return unitSize;
	}
	private int calculateAgentXCoord(AbstractAgent agent) {
	
		return (int) (heatmap[0].length / 2 + (agent.getPossition().cpy().y / unitSize));
	}


	private Vector2 calculatePosition(int i, int j) {
		float ypos = (i - heatmap[0].length / 2) * unitSize;
		float xpos= (j-heatmap[0].length /2)*unitSize;

		return new Vector2(xpos, ypos);
	}


	private int calculateAgentYCoord(AbstractAgent agent) {
	
		return (int) (heatmap.length / 2 + (agent.getPossition().cpy().x / unitSize));
	}

}
