package drawit;

public class PreciseRoundedPolygonContainsTestStrategy {
	boolean contains(RoundedPolygon polygon, IntPoint point) {
		if(point == null || polygon == null) {
			throw new NullPointerException();
		}
		return polygon.contains(point);
	}
}
