package drawit;

public class DoublePoint {
	private double x;
	private double y;
	
	
	public DoublePoint(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public DoublePoint plus(DoubleVector other) {
		DoublePoint result = new DoublePoint(other.x + this.x,other.y + this.y);
		return result;
	}
	
	public DoubleVector minus(DoublePoint other) {
		DoubleVector result = new DoubleVector(other.x - this.x,other.y - this.y);
		return result;
	}
	
	public IntPoint round() {
		IntPoint result = new IntPoint((int) Math.round(this.x),(int)Math.round(this.y));
		return result;
	}
}

