package drawit;

/**
 * An immutable abstraction for a point in the two-dimensional plane with <code>int</code> coordinates.
 * @immutable
 */

public class IntPoint {
	private final int x;
	private final int y;
	
	/** Initializes this point with the given coordinates.
	 * @mutates | this
	 * 
	 * @post The object's X-coordinate is equal to the given X-coordinate
	 * 	| getX() == x
	 * @post The object's Y-coordinate is equal to the given Y-coordinate
	 * 	| getY() == y
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Returns this point's X coordinate. */
	public int getX() {
		return this.x;
	}
	
	/** Returns this point's Y coordinate. */
	public int getY() {
		return this.y;
	}
	
	/** Returns <code>true</code> if this point has the same coordinates as the given point; returns <code>false</code> otherwise.
	 * @pre Argument other should not be null
	 * 	| other != null
	 * @post The result is true if the point is equal, else false
	 * 	| result == (getX() == other.getX() && getY() == other.getY())
	 */
	public boolean equals(IntPoint other) {
		return this.getX() == other.getX() && this.getY() == other.getY();
	}
	
	/** Returns an IntVector object representing the displacement from other to this.
	 * @pre Argument other should not be null
	 * 	| other != null
	 * @post The resulting IntVector's X-coordinate is this object's X-coordinate minus the others X-coordinate
	 * 	| result.getX() == getX() - other.getX()
	 * @post The resulting IntVector's Y-coordinate is this object's Y-coordinate minus the others Y-coordinate
	 * 	| result.getY() == getY() - other.getY()
	 */
	public IntVector minus(IntPoint other) {
		return new IntVector(this.getX()-other.getX(),this.getY()-other.getY());
	}
	
	/** Returns true if point is on open line segment bc. An open line segment does not include its endpoints.
	 * @pre Arguments b and c define proper IntPoints
	 * 	| b != null && c != null
	 */
	public boolean isOnLineSegment(IntPoint b,IntPoint c) {
		if(this.equals(b) || this.equals(c) || b.equals(c)) {
			return false;
		}
		double biggestX = b.getX();
		double smallestX = c.getX();
		if (smallestX > biggestX) {
			biggestX = c.getX();
			smallestX = b.getX();
		}
		double biggestY = b.getY();
		double smallestY = c.getY();
		if (smallestY > biggestY) {
			biggestY = c.getY();
			smallestY = b.getY();
		}
		if (this.x > biggestX || this.x < smallestX || this.y < smallestY || this.y > biggestY) {
			return false;
		}
		if(b.getX() == c.getX()) {
			if(this.x == b.getX() && ((this.getY() > b.getY() && this.getY() < c.getY()) || (this.getY() < b.getY() && this.getY() > c.getY()))) {
				return true;
			}
		} else {
			double rico = (double)(c.y - b.y)/(double)(c.x - b.x);
			if(this.y-b.y == rico*(this.x - b.x)) {
				return true;
			}
		}
		return false;
		

	}
	
	/** Returns a DoublePoint object that represents the same 2D point represented by this IntPoint object.
	 * @creates | result
	 * 
	 * @post The result's X-coordinate should be the same as this object's X-coordinate
	 * 	| result.getX() == getX()
	 * @post The result's Y-coordinate should be the same as this object's Y-coordinate
	 * 	| result.getY() == getY()
	 */
	public DoublePoint asDoublePoint() {
		return new DoublePoint(this.getX(),this.getY());
	}
	
	
	/** Returns an IntPoint object representing the point obtained by displacing this point by the given vector.
	 * @creates | result
	 * 
	 * @pre Argument vector should not be null
	 * 	| vector != null
	 * @post The result's X-coordinate should be the same as this object's X-coordinate
	 * 	| result.getX() == getX() + vector.getX()
	 * @post The result's Y-coordinate should be the same as this object's Y-coordinate
	 * 	| result.getY() == getY() + vector.getY()
	 */
	public IntPoint plus(IntVector vector) {
		IntPoint result =  new IntPoint(this.x + vector.getX(), this.y + vector.getY());
		return result;
	}
	
	/** Returns true iff the open line segment ab intersects the open line segment cd.
	 */
	public static boolean lineSegmentsIntersect(IntPoint a,IntPoint b,IntPoint c,IntPoint d) {
		DoubleVector newVectorac = new DoubleVector(a.x-c.x,a.y-c.y);
		DoubleVector newVectorab = new DoubleVector(a.x-b.x,a.y-b.y);
		DoubleVector newVectorad = new DoubleVector(a.x-d.x,a.y-d.y);
		DoubleVector newVectorca = new DoubleVector(c.x-a.x,c.y-a.y);
		DoubleVector newVectorcb = new DoubleVector(c.x-b.x,c.y-b.y);
		DoubleVector newVectorcd = new DoubleVector(c.x-d.x,c.y-d.y);
		if (Math.signum(newVectorac.crossProduct(newVectorab)) * Math.signum(newVectorad.crossProduct(newVectorab)) < 0 && Math.signum(newVectorca.crossProduct(newVectorcd)) * Math.signum(newVectorcb.crossProduct(newVectorcd)) < 0) {
			return true;
		}
		else {
			return false;
		}
	}
}

