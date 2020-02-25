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
		b
	}
}
