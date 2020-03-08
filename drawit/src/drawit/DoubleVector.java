package drawit;

public class DoubleVector {
	private final double x;
	private final double y;
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public DoubleVector(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public DoubleVector scale(double d) {
		DoubleVector result = new DoubleVector(this.x * d,this.y * d);
		return result;
	}
	
	public DoubleVector plus(DoubleVector other) {
		DoubleVector result = new DoubleVector(this.x + other.x,this.y + other.y);
		return result;
	}
	
	public double getSize() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public double dotProduct(DoubleVector other) {
		return this.getX() * other.getX() + this.getY() * other.getY();
	}
	
	public double crossProduct(DoubleVector other) {
		return this.getX() * other.getY() - this.getY() * other.getX();
	}
	
	public double asAngle() {
		double angle = Math.atan2(this.y, this.x);
		return angle;
	}
}

