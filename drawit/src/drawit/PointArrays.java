package drawit;

public class PointArrays {
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		for(int i = 0; i < points.length; i++) {
			int a = i;
			int b = i+1;
			if (b > points.length - 1) {
				b = b - points.length;
			}
			int c = i+2;
			if (c > points.length - 1) {
				c = c - points.length;
			}
			int d = i+3;
			if (d > points.length - 1) {
				d = d - points.length;
			}
			
			System.out.println(a + " " + b + " " + c + " " + d);
			
			
			if (IntPoint.lineSegmentsIntersect(points[a],points[b],points[c],points[d]) == true) {
				System.out.println("2 lines intersect");
				return "2 lines intersect";
			}
			for(int j = 0; j < points.length; j++) {
				if(j != i && points[i].getX() == points[j].getX() && points[i].getY() == points[j].getY()) {
					System.out.println("2 points are the same");
					return "2 points are the same";
				}
			}
			if (points[b].isOnLineSegment(points[a],points[c])) {
				//a point is not a corner of the polygon
			}
		}
		return null;
	}
	
	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] result = new IntPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			result[i] = points[i];
		}
		return result;
	}
	
	public static IntPoint[] insert(IntPoint[] points,int index,IntPoint point) {
		IntPoint[] result = new IntPoint[points.length+1];
		for(int i = 0; i < index; i++) {
			result[i] = points[i];
		}
		result[index] = point;
		for(int i = index+1; i < result.length; i++) {
			result[i] = points[i-1];
		}
		return result;
	}
	
	public static IntPoint[] remove(IntPoint[] points,int index) {
		IntPoint[] result = new IntPoint[points.length-1];
		for(int i = 0; i < index; i++) {
			result[i] = points[i];
		}
		for(int i = index; i < result.length; i++) {
			result[i] = points[i+1];
		}
		return result;
	}
	
	public static IntPoint[] update(IntPoint[] points,int index,IntPoint value) {
		IntPoint[] result = PointArrays.copy(points);
		PointArrays.remove(points, index);
		PointArrays.insert(points, index, value);
		return result;
	}
}


