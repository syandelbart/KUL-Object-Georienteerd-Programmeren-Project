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
			if (IntPoint.lineSegmentsIntersect(points[a],points[b],points[c],points[d]) == true) {
				return "2 lines intersect";
			}
			for(int j = 0; j < points.length; j++) {
				if(j != i && points[i].getX() == points[j].getX() && points[i].getY() == points[j].getY()) {
					return "2 points are the same";
				}
			}
			if (points[b].isOnLineSegment(points[a],points[c])) {
				return "a point is not a corner of the polygon";
			}
		}
		return null;
	}
}

