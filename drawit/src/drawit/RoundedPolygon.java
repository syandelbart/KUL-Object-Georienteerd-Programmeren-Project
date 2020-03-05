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
		
		for(int i=0;i < this.vertices.length;i++) {
			System.out.print("(" + this.vertices[i].getX() + "-" + this.vertices[i].getY() + ") ");
		}
		
		System.out.println("");
		for(int i = 0;i < this.vertices.length;i++) {
			int index = i;
			int indexPlusOne = i+1;
			int indexPlusTwo = i+2;
			
			if(index > this.vertices.length-1) {
				index -= this.vertices.length;
			}
			
			if(indexPlusOne > this.vertices.length-1) {
				indexPlusOne -= this.vertices.length;
			}
			
			if(indexPlusTwo > this.vertices.length-1) {
				indexPlusTwo -= this.vertices.length;
			}
			
			IntPoint pointA = this.vertices[index];
			IntPoint pointB = this.vertices[indexPlusOne];
			IntPoint pointC = this.vertices[indexPlusTwo];
			
			
			
			//calculating angle between 3 points (angle of B)
			double distancepointApointB = Math.sqrt(Math.pow(pointB.getX()-pointA.getX(),2) + Math.pow(pointB.getY()-pointA.getY(),2));
			double distancepointBpointC = Math.sqrt(Math.pow(pointB.getX()-pointC.getX(),2) + Math.pow(pointB.getY()-pointC.getY(),2));
			double distancepointCpointA = Math.sqrt(Math.pow(pointC.getX()-pointA.getX(),2) + Math.pow(pointC.getY()-pointA.getY(),2));
			double cosAngle = (Math.pow(distancepointCpointA, 2) - Math.pow(distancepointApointB, 2) - Math.pow(distancepointBpointC, 2)) / (2 * distancepointApointB * distancepointBpointC) * -1;
			cosAngle = Math.acos(cosAngle);
			System.out.println(cosAngle);
			
			IntPoint middleAB = new IntPoint((pointB.getX()-pointA.getX())/2,(pointB.getY()-pointA.getY())/2);
			IntPoint middleBC = new IntPoint((pointB.getX()-pointC.getX())/2,(pointB.getY()-pointC.getY())/2);
			
			DoubleVector unitVectorBA;
			DoubleVector unitVectorBC;
			
			//ricoBA
			if(pointB.getX()-pointA.getX() == 0) {
				if(pointB.getX() > pointA.getX()) {
					unitVectorBA = new DoubleVector(0,-1);
				} else {
					unitVectorBA = new DoubleVector(0,1);
				}
			} else {
				double ricoBA;
				if(pointA.getX() > pointB.getX()) {
					ricoBA = (double)(pointA.getY()-pointB.getY())/(double)(pointA.getX()-pointB.getX());
				} else {
					ricoBA = (double)(pointB.getY()-pointA.getY())/(double)(pointB.getX()-pointA.getX());
				}
				double bBA = (2*ricoBA*(pointA.getY()-pointB.getY()-ricoBA*pointA.getX())+2*pointB.getX()) * -1;
				double aBA = Math.pow(ricoBA, 2)+1;
				double cBA = Math.pow(pointA.getY()-pointB.getY()-ricoBA*pointA.getX(), 2) + (Math.pow(pointB.getX(),2)-1);
				
				double discrBA = Math.pow(bBA,2) - (4 * aBA * cBA);
				double BApoint1_x = (-bBA + Math.sqrt(discrBA)) / (2 * aBA);
				double BApoint2_x = (-bBA - Math.sqrt(discrBA)) / (2 * aBA);
				double BApoint1_y = ricoBA * (BApoint1_x - pointA.getX()) + pointA.getY();
				double BApoint2_y = ricoBA * (BApoint2_x - pointA.getX()) + pointA.getY();
				
				DoubleVector BApoint1 = new DoubleVector(BApoint1_x,BApoint1_y);
				DoubleVector BApoint2 = new DoubleVector(BApoint2_x,BApoint2_y);
				
				System.out.println(String.format("pointA: (%d,%d) | pointB: (%d,%d) | pointC: (%d,%d)",pointA.getX(),pointA.getY(),pointB.getX(),pointB.getY(),pointC.getX(),pointC.getY()));
				System.out.println(String.format("RicoBA: %f | discrBA: %f",ricoBA,discrBA));
				System.out.println(String.format("a: %f | b: %f | c: %f", aBA,bBA,cBA));
				System.out.println(String.format("Point 1: %f %f",BApoint1_x,BApoint1_y));
				System.out.println(String.format("Point 2: %f %f",BApoint2_x,BApoint2_y));
				System.out.println("----------------------------------------------");
			}
			
			
			//ricoBC
			if(pointC.getX()-pointB.getX() == 0) {
				if(pointC.getX() > pointB.getX()) {
					unitVectorBC = new DoubleVector(0,1);
				} else {
					unitVectorBC = new DoubleVector(0,-1);
				}
			} else {
				double ricoBC = (pointC.getY()-pointB.getY())/(pointC.getX()-pointB.getX());
			}
			
			
			
			if(pointB.isOnLineSegment(pointA, pointC)) {
				result += String.format("line %d %d %d %d \r\n", pointA.getX(),pointA.getY(),pointB.getX(),pointB.getY());
				result += String.format("line %d %d %d %d \r\n", pointB.getX(),pointB.getY(),pointC.getX(),pointC.getY());
			} else {
				
				
				//DoubleVector unitVectorBA = new DoubleVector(Math.cos(cosAngle/2),Math.sin(cosAngle/2)));
				//DoubleVector unitVectorBC = new DoubleVector(Math.cos(cosAngle/2),Math.sin(cosAngle/2)));
			}
			
			

				
				
			//result += String.format("arc %d %d %d %f %f ",pointB.getX(),pointB.getY(),20,0.0,6.28);			
				
				
				
				

			}
		
		return result;

	}
 	
}

