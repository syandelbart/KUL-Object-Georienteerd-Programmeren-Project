package drawit;

public class FastRoundedPolygonContainsTestStrategy {
	boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.getBoundingBox().contains(point);
	}
}
