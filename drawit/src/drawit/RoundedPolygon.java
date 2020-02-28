package drawit;

public class RoundedPolygon {
	private IntPoint[] vertices;
	private int radius;
	
	public RoundedPolygon() {
		vertices = new IntPoint[0];
	}
	
	public IntPoint[] getVertices() {
		IntPoint[] result = new IntPoint[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			result[i] = vertices[i];
		}
		return result;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void setVertices(IntPoint[] newVertices) {
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) {
			IllegalArgumentException();
		}
		else {
			vertices = new IntPoint[newVertices.length];
			for (int i = 0; i < newVertices.length; i++) {
				vertices[i] = newVertices[i];
			}
		}
	}
	
	public void setRadius(int radius) {
		if (radius < 0) {
			IllegalArgumentException();
		}
		else {
			this.radius = radius;
		}
	}
	
	public void insert(int index,IntPoint point) {
		PointArrays.insert(this.vertices,index,point);
	}
	
	public void remove(int index) {
		PointArrays.remove(vertices,index);
	}
	
	public void update(int index,IntPoint point) {
		PointArrays.update(vertices,index,point);
	}
	
	public boolean contains(IntPoint point) {
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
	
	
}

