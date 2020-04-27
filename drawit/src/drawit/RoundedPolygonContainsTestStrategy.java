package drawit;

public interface RoundedPolygonContainsTestStrategy {
	public class FastRoundedPolygonContainsTestStrategy {
		boolean contains(RoundedPolygon polygon, IntPoint point) {
			return polygon.getBoundingBox().contains(point);
		}
	}
	public class PreciseRoundedPolygonContainsTestStrategy {
		boolean contains(RoundedPolygon polygon, IntPoint point) {
			return polygon.contains(point);
		}
	}
}
