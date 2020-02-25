package drawit;

public class IntVector {
	private int x;
	private int y;
	
	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public long crossProduct(IntVector other) {
		return (long)getX() * other.getY() - (long)getY() * other.getX();
	}
	
	public boolean isCollinearWith(IntVector other) {
		if (this.crossProduct(other) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public long dotProduct(IntVector other) {
		return (long)getX() * other.getX() + (long)getY() * other.getY();
	}
	
	public DoubleVector asDoubleVector() {
		DoubleVector result = new DoubleVector(this.x,this.y);
		return result;
	}
}

