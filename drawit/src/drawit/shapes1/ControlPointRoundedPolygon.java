package drawit.shapes1;
import drawit.IntPoint;
import drawit.RoundedPolygon;

public class ControlPointRoundedPolygon implements ControlPoint{
	int x;
	int y;
	RoundedPolygon shape;
	
	public ControlPointRoundedPolygon(RoundedPolygon shape, int x, int y) {
		this.shape = shape;
		this.x = x;
		this.y = y;

	}
	public drawit.IntPoint getLocation(){
		return new IntPoint(x,y);
	}
	public void remove() {
		IntPoint[] vertices = shape.getVertices();
		for(int i = 0; i < vertices.length; i++) {
			if(vertices[i].equals(new IntPoint(this.x,this.y))) {
				shape.remove(i);
			}
		}
	}
	public void move(drawit.IntVector delta) {
		this.x += delta.getX();
		this.y += delta.getY();
	}
}
