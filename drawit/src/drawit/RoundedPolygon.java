package drawit;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon defined by a set of 2D points with integer coordinates and a nonnegative corner radius.
 * 
 * @invar This object's radius is not smaller than zero
 * 	| 0 <= getRadius()
 */


public class RoundedPolygon {
	
	/**
	 * @invar | 0 <= radius
	 * @invar | vertices != null
	 * @invar | Arrays.stream(vertices).allMatch(e -> e != null)
	 */
	private IntPoint[] vertices;
	private int radius;
	
	/** 
	 * Initializes the polygon with an empty list of points and a corner radius of 0.
	 * @mutates | this
	 * @post This object has an empty list of vertices
	 * 	| getVertices().length == 0
	 * @post This object has a radius of 0.
	 * 	| getRadius() == 0
	 */
	public RoundedPolygon() {
		this.vertices = new IntPoint[0];
		this.radius = 0;
		
	}
	
	/**Returns a new array whose elements are the vertices of this rounded polygon.
	 * @post The result's elements are not null
	 *	| Arrays.stream(result).allMatch(e -> e != null)
	 */
	public IntPoint[] getVertices() {
		IntPoint[] result = new IntPoint[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			result[i] = vertices[i];
		}
		return result;
	}
	
	/**
	 * Returns the radius of the corners of this rounded polygon.
	 */
	public int getRadius() {
		return this.radius;
	}
	
	/**Sets the vertices of this rounded polygon to be equal to the elements of the given array.
	 * @mutates | this
	 * @inspects | other
	 * 
	 * @throws IllegalArgumentException if the given array of vertices does not resemble a valid polygon
	 * 	| !(PointArrays.checkDefinesProperPolygon(newVertices) == null)
	 * @throws IllegalArgumentException if the given point is a null pointer
	 * | !(newVertices != null)
	 * @post The objects vertices should be equal to that of the argument newVertices
	 * 	| IntStream.range(0, newVertices.length).allMatch(i -> newVertices[i].equals(getVertices()[i]))
	 * @post The amount of vertices for the object should be the same as the length of the argument newVertices
	 * 	| getVertices().length == newVertices.length
	 */
	public void setVertices(IntPoint[] newVertices) {
		if (!(PointArrays.checkDefinesProperPolygon(newVertices) == null)) {
			throw new IllegalArgumentException();
		} else if(!(newVertices != null)) {
			throw new IllegalArgumentException();
		} else {
			vertices = new IntPoint[newVertices.length];
			for (int i = 0; i < newVertices.length; i++) {
				vertices[i] = newVertices[i];
			}
		}
	}
	
	/**Sets this rounded polygon's corner radius to the given value.
	 * @mutates | this
	 * @inspects | radius
	 * 
	 * @throws IllegalArgumentException if the given radius is smaller than 0
	 * 	| !(0 <= radius)
	 * @post The radius of this object should have been changed to the argument radius
	 * 	| getRadius() == radius
	 */
	public void setRadius(int radius) {
		if (!(0 <= radius)) {
			throw new IllegalArgumentException();
		} else {
			this.radius = radius;
		}
	}
	
	
	/** Inserts a given point at given index of the vertices.
	 * @mutates | this
	 * @inspects | index
	 * @inspects | point
	 * 
	 * @throws IllegalArgumentException if the given index is smaller than 0
	 * | !(0 <= index)
	 * @throws IllegalArgumentException if the given index is greater than the length of the array
	 * 	| !(index <= this.vertices.length)
	 * @throws IllegalArgumentException if the given point is a null pointer
	 * | !(point != null)
	 * @throws IllegalArgumentException if the inserted point makes the polygon improper (this will also result in a "reset" of the polygon to its original form)
	 * | !(PointArrays.checkDefinesProperPolygon(this.getVertices()) == null)
	 * @post The object's new length should be one more than before.
	 * 	| getVertices().length == old(getVertices()).length + 1
	 * @post Every point that comes before index should remain the same
	 *  | Arrays.equals(getVertices(), 0, index,old(getVertices()), 0, index)
	 * @post The point at the index should be the point that has been provided as a parameter.
	 * 	| getVertices()[index].equals(point)
	 * @post Every point that comes after the index should have an index that is 1 higher than initially.
	 *  | Arrays.equals(old(getVertices()), index, old(getVertices()).length, getVertices(), index+1, getVertices().length)
	 */
	public void insert(int index,IntPoint point) {
		if(!(index <= this.vertices.length) || !(0 <= index)) {
			throw new IllegalArgumentException();
		} else if(!(point != null)) {
			throw new IllegalArgumentException();
		} else {
			this.setVertices(PointArrays.insert(this.getVertices(), index, point));
			if(!(PointArrays.checkDefinesProperPolygon(this.getVertices()) == null)) {
				this.setVertices(PointArrays.remove(this.getVertices(), index));
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * @throws IllegalArgumentException if the given index is smaller than 0
	 * | !(0 <= index)
	 * @throws IllegalArgumentException if the given index is greater than the length of the array minus one
	 * 	| !(index <= getVertices().length-1)
	 * @throws IllegalArgumentException if one of the last 3 points is to be removed, this we do since it would make an invalid polygon
	 *  | !(getVertices().length > 3)
	 */
	public void remove(int index) {
		if(!(index <= this.vertices.length-1) || !(this.vertices.length > 3) || !(0 <= index)) {
			throw new IllegalArgumentException();
		} else {
			this.vertices = PointArrays.remove(this.vertices,index);
		}
		
	}
	
	/** Updates a point at a given index to the given point.
	 * @throws IllegalArgumentException if the given index is smaller than 0
	 * | !(0 <= index)
	 * @throws IllegalArgumentException if the given index is greater than the length of the array minus one
	 * | !(index <= this.vertices.length-1)
	 * @throws IllegalArgumentException if the given point is a null pointer
	 * | !(point != null)
	 * @post The object's new length should be the same as before
	 * | old(getVertices()).length == getVertices().length
	 * @post Every point that comes before index should remain the same
	 * 	| IntStream.range(0, index).allMatch(i -> old(getVertices())[i].equals(getVertices()[i]))
	 * @post The point at the index should be the point that has been provided as a parameter.
	 * 	| getVertices()[index].equals(point)
	 * @post Every point that comes after the index should remain the same
	 * 	| IntStream.range(index+1, getVertices().length).allMatch(i -> old(getVertices())[i].equals(getVertices()[i]))
	 */
	public void update(int index,IntPoint point) {
		if(!(index <= this.vertices.length-1) || !(0 <= index)) {
			throw new IllegalArgumentException();
		} else if(!(point != null)) {
			throw new IllegalArgumentException();
		} else {
			vertices = PointArrays.update(vertices,index,point);
		}
	}
	
	/**Returns true iff the given point is contained by the (non-rounded) polygon defined by this rounded polygon's vertices. 
	 * This method does not take into account this rounded polygon's corner radius; it assumes a corner radius of zero.
	 * A point is contained by a polygon if it coincides with one of its vertices, or if it is on one of its edges, or if it is in the polygon's interior.
	 * @throws IllegalArgumentException if the given point is a null pointer
	 *  | !(point != null)
	 */
	public boolean contains(IntPoint point) {
		if(!(point != null)) {
			throw new IllegalArgumentException();
		}
		
		int biggestX = vertices[0].getX();
		for(int i = 0; i < vertices.length; i++) {
			if (point.equals(vertices[i])) {
				return true;
			}
			int a = i;
			int b = i+1;
			if (b > vertices.length - 1) {
				b = b - vertices.length;
			}
			if (point.isOnLineSegment(vertices[a],vertices[b])) {
				return true;
			}
			if (vertices[i].getX() > biggestX) {
				biggestX = vertices[i].getX();
			}
		}
		int intersections = 0;
		IntPoint newPoint = new IntPoint(biggestX+1,point.getY());
		for(int i = 0; i < vertices.length; i++) {
			int a = i;
			int b = i+1;
			if (b > vertices.length - 1) {
				b = b - vertices.length;
			}
			int c = i+2;
			if (c > vertices.length - 1) {
				c = c - vertices.length;
			}
			if (IntPoint.lineSegmentsIntersect(point,newPoint,vertices[a],vertices[b])) {
				intersections += 1;
			}
			if (vertices[b].isOnLineSegment(point,newPoint)) {
				if ((point.getY() < vertices[a].getY() && point.getY() > vertices[c].getY()) || (point.getY() > vertices[a].getY() && point.getY() < vertices[c].getY())) {
					intersections += 1;
				}
			}
		}
		if (intersections % 2 != 0) {
			return true;
		}
		return false;
	}
	
	/** Returns a textual representation of a set of drawing commands for drawing this rounded polygon. */
	public String getDrawingCommands() {
		if(this.vertices.length < 3) {
			return "";
		}
		String result = "";

		IntPoint[] pointArray = new IntPoint[0];
		for(int i = 0;i < this.vertices.length;i++) {
			int index = i;
			int indexPlusOne = i+1;
			int indexPlusTwo = i+2;
			
			if(index > this.vertices.length-1) {
				index -= this.vertices.length;
			}
			
			if(indexPlusOne > this.vertices.length-1) {
				indexPlusOne -= this.vertices.length;
			}
			
			if(indexPlusTwo > this.vertices.length-1) {
				indexPlusTwo -= this.vertices.length;
			}
			
			DoublePoint pointA = this.vertices[index].asDoublePoint();
			DoublePoint pointB = this.vertices[indexPlusOne].asDoublePoint();
			DoublePoint pointC = this.vertices[indexPlusTwo].asDoublePoint();
			
			//calculating angle between 3 points (angle of B)
			double distancepointApointB = Math.sqrt(Math.pow(pointB.getX()-pointA.getX(),2) + Math.pow(pointB.getY()-pointA.getY(),2));
			double scaleBA = 1 / distancepointApointB;
			double distancepointBpointC = Math.sqrt(Math.pow(pointB.getX()-pointC.getX(),2) + Math.pow(pointB.getY()-pointC.getY(),2));
			double scaleBC = 1 / distancepointBpointC;

			DoubleVector VectorBA = new DoubleVector(pointA.getX() - pointB.getX(),pointA.getY() - pointB.getY());
			DoubleVector VectorBC = new DoubleVector(pointC.getX() - pointB.getX(),pointC.getY() - pointB.getY());
			double shortestDistance = distancepointApointB;
			if (shortestDistance > distancepointBpointC) {
				shortestDistance = distancepointBpointC;
			}
			DoubleVector unitVectorBA = VectorBA.scale(scaleBA);
			DoubleVector unitVectorBC = VectorBC.scale(scaleBC);
			DoubleVector bissectrice = unitVectorBA.plus(unitVectorBC);
			//result += "line" + " " + pointB.getX() + " " + pointB.getY() + " " + (pointB.getX() + 10 * bissectrice.getX()) + " " + (pointB.getY() + 10 * bissectrice.getY()) + "\n";
			DoublePoint centre = pointB.plus(bissectrice);
			double cutoff = bissectrice.dotProduct(unitVectorBA);
			double smallRadius = bissectrice.crossProduct(unitVectorBA);
			if (smallRadius < 0) {
				smallRadius = smallRadius * -1;
			}
			double finalscale1 = this.getRadius() / smallRadius;
			double finalscale2 = shortestDistance / (2 * cutoff);
			DoublePoint point1;
			DoublePoint point2;
			if (finalscale1 < finalscale2) {
				cutoff = cutoff * finalscale1;
				centre = new DoublePoint(centre.getX() + ((finalscale1-1) * bissectrice.getX()),centre.getY() + ((finalscale1-1) * bissectrice.getY()));
				smallRadius = smallRadius * finalscale1;
				point1 = new DoublePoint(pointB.getX() + cutoff*unitVectorBA.getX(), pointB.getY() + cutoff*unitVectorBA.getY());
				point2 = new DoublePoint(pointB.getX() + cutoff*unitVectorBC.getX(), pointB.getY() + cutoff*unitVectorBC.getY());

			}
			else {
				cutoff = cutoff * finalscale2;
				centre = new DoublePoint(centre.getX() + ((finalscale2-1) * bissectrice.getX()),centre.getY() + ((finalscale2-1) * bissectrice.getY()));
				smallRadius = smallRadius * finalscale2;
				point1 = new DoublePoint(pointB.getX() + cutoff*unitVectorBA.getX(), pointB.getY() + cutoff*unitVectorBA.getY());
				point2 = new DoublePoint(pointB.getX() + cutoff*unitVectorBC.getX(), pointB.getY() + cutoff*unitVectorBC.getY());			}
			// this will show lines from B to centre
			//result += "line" + " " + pointB.getX() + " " + pointB.getY() + " " + centre.getX() + " " + centre.getY() + "\n";
			DoubleVector fromMiddle1 = new DoubleVector(point1.getX() - centre.getX(),point1.getY() - centre.getY());
			DoubleVector fromMiddle2 = new DoubleVector(point2.getX() - centre.getX(),point2.getY() - centre.getY());
			
			if (VectorBA.crossProduct(VectorBC) != 0) {
				pointArray = PointArrays.insert(pointArray,pointArray.length,point1.round());
				pointArray = PointArrays.insert(pointArray,pointArray.length,point2.round());
			}
			
			double startAngle = fromMiddle1.asAngle();
			double endAngle = fromMiddle2.asAngle();
			double extend = endAngle - startAngle;
			if (extend > 3.14) {
				extend -= 6.28;
			}
			if (extend < -3.14) {
				extend += 6.28;
			}
			result += "arc" + " " + centre.getX() + " " + centre.getY() + " " + smallRadius + " " + startAngle + " " + extend + "\n";
			}
		for (int i = 0; i < pointArray.length; i++) {
			if (i % 2 != 0) {
				int index = i;
				int indexPlusOne = i+1;
				if(indexPlusOne > pointArray.length-1) {
					indexPlusOne -= pointArray.length;
				}
				result += "line" + " " + pointArray[index].getX() + " " + pointArray[index].getY() + " " + pointArray[indexPlusOne].getX() + " " + pointArray[indexPlusOne].getY() + "\n";
			}
		}
		

		return result;

	}
 	
}


