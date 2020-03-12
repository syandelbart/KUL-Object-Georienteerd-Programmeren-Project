package drawit;

/**
 * An instance of this class represents a displacement in the two-dimensional plane.
 * @immutable
 */

public class IntVector {
	private final int x;
	private final int y;
	
	/** Initializes this vector with the given coordinates.
	 * @mutates | this
	 * 
	 * @post The object's X-coordinate is equal to the given X-coordinate
	 * 	| this.getX() == x
	 * @post The object's Y-coordinate is equal to the given Y-coordinate
	 * 	| this.getY() == y
	 */
	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Returns this vector's X coordinate. */
	public int getX() {
		return this.x;
	}
	
	/** Returns this vector's Y coordinate. */
	public int getY() {
		return this.y;
	}
	
	/** Returns the cross product of this vector and the given vector.
	 * @pre Argument other should not be null
	 * 	| other != null
	 * @post The result should be x1*y2 - x2*y1
	 * 	| result == getX() * other.getY() - getY() * other.getX()
	 */
	public long crossProduct(IntVector other) {
		return (long)this.getX() * other.getY() - (long)this.getY() * other.getX();
	}
	
	/** Returns whether this vector is collinear with the given vector.
	 * @pre Argument other should not be null
	 * 	| other != null
	 * @post result is true if the crossproduct of this object with other is 0
	 * 	| result == (crossProduct(other) == 0)
	 */
	public boolean isCollinearWith(IntVector other) {
		return this.crossProduct(other) == 0;
	}
	
	/** Returns the dot product of this vector and the given vector.
	 * @pre Argument other should not be null
	 * 	| other != null
	 * @post The result should be x1*x2 + y1*y2
	 * 	| result == getX() * other.getX() + getY() * other.getY()
	 */
	public long dotProduct(IntVector other) {
		return (long)this.getX() * other.getX() + (long)this.getY() * other.getY();
	}
	
	/** Returns a DoubleVector object that represents the same vector represented by this IntVector object.
	 * @creates | still have to fill in
	 * 
	 * @post The result's X-coordinate should be the same as this object's X-coordinate
	 * 	| result.getX() == getX()
	 * @post The result's Y-coordinate should be the same as this object's Y-coordinate
	 * 	| result.getY() == getY()
	 */
	public DoubleVector asDoubleVector() {
		return new DoubleVector(this.x,this.y);
	}
}

