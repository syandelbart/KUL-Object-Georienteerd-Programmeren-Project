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
			double scaleBA = 1 / distancepointApointB;
			double distancepointBpointC = Math.sqrt(Math.pow(pointB.getX()-pointC.getX(),2) + Math.pow(pointB.getY()-pointC.getY(),2));
			double scaleBC = 1 / distancepointBpointC;
			double distancepointCpointA = Math.sqrt(Math.pow(pointC.getX()-pointA.getX(),2) + Math.pow(pointC.getY()-pointA.getY(),2));
			double cosAngle = (Math.pow(distancepointCpointA, 2) - Math.pow(distancepointApointB, 2) - Math.pow(distancepointBpointC, 2)) / (2 * distancepointApointB * distancepointBpointC) * -1;
			cosAngle = Math.acos(cosAngle);
			System.out.println(cosAngle);
			
			IntPoint middleAB = new IntPoint((pointB.getX()-pointA.getX())/2,(pointB.getY()-pointA.getY())/2);
			IntPoint middleBC = new IntPoint((pointB.getX()-pointC.getX())/2,(pointB.getY()-pointC.getY())/2);
			
			DoubleVector VectorBA = new DoubleVector(pointA.getX() - pointB.getX(),pointA.getY() - pointB.getY());
			DoubleVector VectorBC = new DoubleVector(pointC.getX() - pointB.getX(),pointC.getY() - pointB.getY());
			double shortestDistance = distancepointApointB;
			if (shortestDistance > distancepointBpointC) {
				shortestDistance = distancepointBpointC;
			}
			DoubleVector unitVectorBA = VectorBA.scale(scaleBA);
			DoubleVector unitVectorBC = VectorBC.scale(scaleBC);
			DoubleVector bissectrice = unitVectorBA.plus(unitVectorBC);
			DoublePoint centre = pointB.asDoublePoint().plus(bissectrice);
			double cutoff = 2 * bissectrice.dotProduct(unitVectorBA);
			double smallRadius = bissectrice.crossProduct(unitVectorBA);
			double finalscale1 = this.getRadius() / smallRadius;
			double finalscale2 = shortestDistance / (2 * cutoff);
			DoublePoint point1;
			DoublePoint point2;
			if (finalscale1 < finalscale2) {
				centre = new DoublePoint(centre.getX() + (finalscale1 * bissectrice.getX()),centre.getY() + (finalscale1 * bissectrice.getY()));
				smallRadius = smallRadius * finalscale1;
				point1 = new DoublePoint(pointB.getX() + unitVectorBA.scale(finalscale1).getX(), pointB.getY() + unitVectorBA.scale(finalscale1).getY());
				point2 = new DoublePoint(pointB.getX() + unitVectorBC.scale(finalscale1).getX(), pointB.getY() + unitVectorBC.scale(finalscale1).getY());

			}
			else {
				centre = new DoublePoint(centre.getX() + (finalscale2 * bissectrice.getX()),centre.getY() + (finalscale2 * bissectrice.getY()));
				smallRadius = smallRadius * finalscale2;
				point1 = new DoublePoint(pointB.getX() + unitVectorBA.scale(finalscale2).getX(), pointB.getY() + unitVectorBA.scale(finalscale2).getY());
				point2 = new DoublePoint(pointB.getX() + unitVectorBC.scale(finalscale2).getX(), pointB.getY() + unitVectorBC.scale(finalscale2).getY());
			}
			DoubleVector toMiddle1 = new DoubleVector(centre.getX() - point1.getX(),centre.getY() - point1.getY());
			DoubleVector toMiddle2 = new DoubleVector(centre.getX() - point2.getX(),centre.getY() - point2.getY());
			double startAngle;
			double extend;
			if (toMiddle1.asAngle() < toMiddle2.asAngle()) {
				startAngle = toMiddle1.asAngle() + 3.14;
				extend = toMiddle2.asAngle() - toMiddle1.asAngle();
			}
			else {
				startAngle = toMiddle2.asAngle() + 3.14;
				extend = toMiddle1.asAngle() - toMiddle2.asAngle();
			}
			System.out.println(extend);
			System.out.println("----------------------------------------------");
			result += "arc" + " " + centre.getX() + " " + centre.getY() + " " + smallRadius + " " + startAngle + " " + extend + "\n";
			result += "line" + " " + pointA.getX() + " " + pointA.getY() + " " + pointB.getX() + " " + pointB.getY() + "\n";
			result += "line" + " " + (pointB.getX() + 10 * bissectrice.getX()) + " " + (pointB.getY() + 10 * bissectrice.getY()) + " " + pointB.getX() + " " + pointB.getY() + "\n";
			}
		
		return result;

	}
 	
}

