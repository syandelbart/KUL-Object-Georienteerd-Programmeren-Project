<<<<<<< HEAD
=======
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
			throw new IllegalArgumentException();
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
			throw new IllegalArgumentException();
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
	
	
	public String getDrawingCommands() {
		if(this.vertices.length < 3) {
			return "";
		}
		String result = "";
		
		for(int i = 0;i < this.vertices.length - 2;i++) {
			IntPoint point1 = this.vertices[i];
			IntPoint point2 = this.vertices[i+1];
			IntPoint point3 = this.vertices[i+2];
			
			
			if(point2.isOnLineSegment(point1, point3)) {
				result += String.format("line %d %d %d %d \r\n", point1.getX(),point1.getY(),point3.getX(),point3.getY());
			} else {
				result += String.format("line %d %d %d %d \r\n", point1.getX(),point1.getY(),point2.getX(),point2.getY());
				
				double distancePoint2Point1 = Math.sqrt(Math.pow(point2.getX()-point1.getX(),2) + Math.pow(point2.getY()-point1.getY(),2));
				double distancePoint2Point3 = Math.sqrt(Math.pow(point2.getX()-point3.getX(),2) + Math.pow(point2.getY()-point3.getY(),2));
				double radiansCorner = distancePoint2Point1/distancePoint2Point3;
				System.out.println(distancePoint2Point1);
				
				if(point2.getX() < point1.getX()) {
					if(point2.getY() < point1.getY()) {
						
					} else {
						
					}
				}
				
				
				if(point1.getX() > point2.getX()) {
					result += String.format("arc %d %d %d %f %f ",point1.getX(),point2.getY(),20,0.0,6.28);
				} else {
					result += String.format("arc %d %d %d %f %f ",point2.getX(),point2.getY(),20,0.0,6.28);
				}
				
				
				
				result += String.format("line %d %d %d %d \r\n", point2.getX(),point2.getY(),point3.getX(),point3.getY());
			}
		}
		
		return result;

	}
 	
}

>>>>>>> branch 'master' of https://github.com/Syantje/java-project-p1
