package drawit;

public interface RoundedPolygonContainsTestStrategy {
	/** 
	 * Returns if the given polygon's shape contains the given point or if the given polygon's bounding box contains the given point
	 * 
	 * @post Returns if the given polygon's shape contains the given point or if the given polygon's bounding box contains the given point
	 * |result == polygon.contains(point) || result == polygon.getBoundingBox().contains(point)
	 */
	boolean contains(RoundedPolygon polygon, IntPoint point);
}
