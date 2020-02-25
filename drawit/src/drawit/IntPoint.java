package drawit;

public class IntPoint {
	private int x;
	private int y;
	
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean equals(IntPoint other) {
		if(other.x == this.x && other.y == this.y) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public IntVector minus(IntPoint other) {
		IntVector result = new IntVector(this.getX()-other.getX(),this.getY()-other.getY());
		return result;
	}
	
	public boolean isOnLineSegment(IntPoint b,IntPoint c) {
		int rico = (c.y - b.y)/(c.x - b.x);
		if(this.equals(b) || this.equals(c)) {
			return false;
		}
		if(this.y-b.y == rico*(this.x - b.x)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public DoublePoint asDoublePoint() {
		DoublePoint result = new DoublePoint(this.getX(),this.getY());
		return result;
	}
	
	public IntPoint plus(IntVector vector) {
		IntPoint result = new IntPoint(this.x + vector.x, this.y + vector.y);
		return result;
	}
	
	public static boolean lineSegmentsIntersect(IntPoint a,IntPoint b,IntPoint c,IntPoint d) {
		DoubleVector newVectorac = new DoubleVector(a.x-c.x,a.y-c.y);
		DoubleVector newVectorab = new DoubleVector(a.x-b.x,a.y-b.y);
		DoubleVector newVectorad = new DoubleVector(a.x-d.x,a.y-d.y);
		if (Math.signum(newVectorac.crossProduct(newVectorab)) * Math.signum(newVectorad.crossProduct(newVectorab)) < 0) {
			return true;
		}
		else {
			return false;
		}
	}
}

