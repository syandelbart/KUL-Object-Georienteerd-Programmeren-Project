package drawit.shapes1;
import drawit.IntPoint;
import drawit.RoundedPolygon;

public class ControlPointRoundedPolygon implements ControlPoint{
	int location;
	IntPoint point;
	RoundedPolygon shape;
	
	public ControlPointRoundedPolygon(RoundedPolygon shape, IntPoint point, int location) {
		this.shape = shape;
		this.point = point;
		this.location = location;

	}
	public drawit.IntPoint getLocation(){
		return new IntPoint(point.getX(),point.getY());
	}
	public void remove() {
		IntPoint[] vertices = shape.getVertices();
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i].equals(this.point)) {
				shape.remove(i);
			}
		}
	}
	public void move(drawit.IntVector delta) {
		shape.update(location,this.point.plus(delta));
	}
}
