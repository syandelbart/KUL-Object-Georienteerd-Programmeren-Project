package drawit;

public class PreciseRoundedPolygonContainsTestStrategy {
	/** 
	 * Returns if the given polygon's shape contains the given point
	 * 
	 * @post Returns if the given polygon's shape contains the given point
	 * |result == polygon.contains(point)
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		if(point == null || polygon == null) {
			throw new NullPointerException();
		}
		return polygon.contains(point);
	}
}
