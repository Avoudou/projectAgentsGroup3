package game.systems.aiSubSystems;

import game.systems.systemUtil.AABBFindAllObstaclesCallBack;
import agentDefinitions.AgentWorld;

import com.badlogic.gdx.math.Vector2;

public class ObstacleMapper {
	public void mapObstacles(AgentWorld world, double[][] heatmap, int unitSize) {
		for (int i = 0; i < heatmap.length; i++) {
			for (int j = 0; j < heatmap[0].length; j++) {

				AABBFindAllObstaclesCallBack obstacles = new AABBFindAllObstaclesCallBack(world, calculatePos(i, j,
						heatmap, unitSize), unitSize / 2);

				Vector2 position = new Vector2();
				world.getPhysicsWorld().QueryAABB(obstacles, position.x, position.y, position.x + unitSize,
						position.y + unitSize);
				if (obstacles.getDetectedObstacles().size() > 0) {
					heatmap[i][j] = -100000;
				}

			}

		}


	}

	private Vector2 calculatePos(int i, int j, double[][] heatmap, int unitSize) {
		float ypos = (i - heatmap[0].length / 2) * unitSize;
		float xpos = (j - heatmap[0].length / 2) * unitSize;

		return new Vector2(xpos, ypos);

	}

}
