package drawit;

public class FastRoundedPolygonContainsTestStrategy {
	/** 
	 * Returns if the given polygon's bounding box contains the given point
	 * 
	 * @post Returns if the given polygon's bounding box contains the given point
	 * |result == polygon.getBoundingBox().contains(point)
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.getBoundingBox().contains(point);
	}
}
