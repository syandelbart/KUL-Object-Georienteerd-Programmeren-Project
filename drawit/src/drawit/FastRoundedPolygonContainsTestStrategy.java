package drawit;

public class FastRoundedPolygonContainsTestStrategy {
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.getBoundingBox().contains(point);
	}
}
