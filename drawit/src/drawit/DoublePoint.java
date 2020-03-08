package drawit;

public class DoublePoint {
	private final double x;
	private final double y;
	
	
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
		DoublePoint result = new DoublePoint(other.getX() + this.x,other.getY() + this.y);
		return result;
	}
	
	public DoubleVector minus(DoublePoint other) {
		DoubleVector result = new DoubleVector(this.x - other.x,this.y - other.y);
		return result;
	}
	
	public IntPoint round() {
		IntPoint result = new IntPoint((int) Math.round(this.x),(int)Math.round(this.y));
		return result;
	}
}

