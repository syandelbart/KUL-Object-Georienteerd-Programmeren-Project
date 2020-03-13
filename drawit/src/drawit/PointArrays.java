package drawit;
import java.util.stream.IntStream;

/**
 * Declares a number of methods useful for working with arrays of <code>IntPoint</code> objects.
 */

public class PointArrays {
	//Public constructor should not be generated, therefore we create a private constructor.
	private PointArrays() {}
	
	/** Returns null if the given array of points defines a proper polygon; otherwise, returns a string describing why it does not.
	 *  We interpret an array of N points as the polygon whose vertices are the given points and whose edges are the open line segments between point I and point (I + 1) % N, for I = 0, ..., N - 1.
	 *  A proper polygon is one where no two vertices coincide and no vertex is on any edge and no two edges intersect.
	 *  If there are exactly two points, the polygon is not proper. Notice that if there are more than two points and no two vertices coincide and no vertex is on any edge, then no two edges intersect at more than one point.
	 */
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		if(points.length < 3) {
			//not enough points so makes the roundedpolygon improper
			return "not enough points";
		}
		
		for(int i = 0; i < points.length; i++) {
			int a = i;
			int b = i+1;
			if (b > points.length - 1) {
				b = b - points.length;
			}
			int c = i+2;
			if (c > points.length - 1) {
				c = c - points.length;
			}
			int d = i+3;
			if (d > points.length - 1) {
				d = d - points.length;
			}
			
			for(int i2 = 0; i2 < points.length; i2++) {
				int a2 = i2;
				int b2 = i2+1;
				if (b2 > points.length - 1) {
					b2 = b2 - points.length;
				}
				if (points[a].isOnLineSegment(points[a2],points[b2])) {
					return "vertex is on edge";
				}
				if (IntPoint.lineSegmentsIntersect(points[a2],points[b2],points[a],points[b]) == true && a2 != a && b2 != b) {
					return "2 lines intersect";
				}
			}

			for(int j = 0; j < points.length; j++) {
				if(j != i && points[i].getX() == points[j].getX() && points[i].getY() == points[j].getY()) {
					return "2 points are the same";
				}
				
			}

			
			if (points[b].isOnLineSegment(points[a],points[c])) {
				//a point is not a corner of the polygon
				return "cannot place point on corner";
			}
		}
		return null;
	}
	
	/** Returns a new array with the same contents as the given array.
	 * @creates | result
	 * 
	 * @post The length of the resulting array should be the same as the length of the initial array
	 * 	| result.length == points.length
	 * @post Every element of the initial array should have the same value as the resulting array
	 * 	| IntStream.range(0, result.length).allMatch(i -> points[i].equals(result[i]))
	 */
	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] result = new IntPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			result[i] = points[i];
		}
		return result;
	}
	
	/** Returns a new array whose elements are the elements of the given array with the given point inserted at the given index.
	 * @creates | result
	 * 
	 * @pre Argument index is not bigger than the length of the array.
	 * 	| 0 <= index && index <= points.length
	 * @pre Argument point is not null
	 * 	| point != null
	 * @post Since a point has been added to the array, the array should be 1 longer than the initial length of the input array.
	 * 	| result.length == points.length + 1
	 * @post Every point that comes before index should remain the same
	 * 	| IntStream.range(0, index).allMatch(i -> points[i].equals(result[i]))
	 * @post The point at the index should be the point that has been provided as a parameter.
	 * 	| result[index].equals(point)
	 * @post Every point that comes after the index should have an index that is 1 higher than initially.
	 * 	| IntStream.range(index+1, result.length).allMatch(i -> points[i-1].equals(result[i]))
	 */
	public static IntPoint[] insert(IntPoint[] points,int index,IntPoint point) {
		IntPoint[] result = new IntPoint[points.length+1];
		for(int i = 0; i < index; i++) {
			result[i] = points[i];
		}
		result[index] = point;
		for(int i = index+1; i < result.length; i++) {
			result[i] = points[i-1];
		}
		return result;
	}
	
	/** Returns a new array whose elements are the elements of the given array with the element at the given index removed.
	 * @creates | result
	 * 
	 * @pre Argument points's size is bigger than 0
	 * 	| 0 < points.length 
	 * @pre Argument index is not bigger than the size of the array minus one.
	 * 	| 0 <= index && index < points.length
	 * @post Since a point has been removed from the array, the array should be 1 shorter than the initial length of the input array.
	 * 	| result.length == points.length - 1
	 * @post Every point that comes before index should remain the same
	 * 	| IntStream.range(0, index).allMatch(i -> points[i].equals(result[i]))
	 * @post Every point that comes after the index should have an index that is 1 smaller than initially.
	 * 	| IntStream.range(index, result.length).allMatch(i -> points[i+1].equals(result[i]))
	 */
	public static IntPoint[] remove(IntPoint[] points,int index) {
		IntPoint[] result = new IntPoint[points.length-1];
		for(int i = 0; i < index; i++) {
			result[i] = points[i];
		}
		for(int i = index; i < result.length; i++) {
			result[i] = points[i+1];
		}
		return result;
	}
	
	/** Returns a new array whose elements are the elements of the given array with the element at the given index replaced by the given point.
	 * @creates | result
	 * 
	 * @pre Argument points's size is bigger than 0
	 * 	| 0 < points.length 
	 * @pre Argument index is not bigger than the size of the array minus one.
	 * 	| 0 <= index && index < points.length
	 * @pre Argument value should not be null
	 * 	| value != null
	 * @post The length of the resulting array should be the same as the length of the initial array.
	 * 	| result.length == points.length
	 * @post Every point that comes before index should remain the same
	 * 	| IntStream.range(0, index).allMatch(i -> points[i].equals(result[i]))
	 * @post The point at index should be updated with the provided parameter value
	 * 	| result[index].equals(value)
	 * @post Every point that comes after the index should remain the same.
	 * 	| IntStream.range(index+1, result.length).allMatch(i -> points[i].equals(result[i]))
	 */
	public static IntPoint[] update(IntPoint[] points,int index,IntPoint value) {
		IntPoint[] result = PointArrays.copy(points);
		result = PointArrays.remove(result, index);
		result = PointArrays.insert(result, index, value);
		return result;
	}
}


